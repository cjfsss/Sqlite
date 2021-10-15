package hos.sqlite.statement;

import android.database.SQLException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;

import java.util.List;
import java.util.Map;

import hos.sqlite.SQLite;
import hos.sqlite.SQLiteExecutor;
import hos.sqlite.datebase.ConflictAlgorithm;
import hos.sqlite.datebase.SqlBuilder;
import hos.utils.CloseUtils;

/**
 * <p>Title: SQLiteDatabase </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/19 10:52
 */
public abstract class SQLiteDatabase extends Transaction implements SQLite,
        SQLiteExecutor,
        SQLTransaction {


    protected abstract <STATEMENT extends SQLiteStatement<STATEMENT>> SQLiteStatement<STATEMENT> compileStatement(@NonNull final String sql);

    @Override
    public long update(@NonNull String table, @NonNull Map<String, Object> values, @NonNull String whereClause,
                      @Nullable Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        SqlBuilder update = new SqlBuilder().update(table, values, whereClause, whereArgs, conflictAlgorithm);
        return statement(update.sql, update.arguments, statement -> {
            final long count;
            if ((count = statement.executeUpdateDelete()) <= 0) {
                Log.w("database", "更新数据出错，错误数据是:" + statement.toString());
            }
            return count;
        });
    }

    @Override
    public long update(@NonNull String table, @NonNull List<Map<String, Object>> valueList, @NonNull String whereClause,
                      @Nullable Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return transaction(new Function<SQLiteDatabase, Long>() {
            @Override
            public Long apply(SQLiteDatabase input) {
                @SuppressWarnings("WrapperTypeMayBePrimitive") Long updateSize = 0L;
                for (Map<String, Object> map : valueList) {
                    updateSize += update(table, map, whereClause, whereArgs, conflictAlgorithm);
                }
                return updateSize;
            }
        });
//        int updateSize = 0;
//        for (Map<String, Object> map : valueList) {
//            updateSize += update(table, map, whereClause, whereArgs, conflictAlgorithm);
//        }
//        return updateSize;
//        final int size = valueList.size();
//        if (size == 0) {
//            Log.e("database", "Error update " + table + " values null");
//            return -1;
//        }
//        SqlBuilder update = new SqlBuilder().updateList(table, valueList.get(0), whereClause, whereArgs, conflictAlgorithm);
//        return (int) statement(update.sql, statement -> {
//            long count = 0;
//            for (Map<String, Object> map : valueList) {
//                try {
//                    statement.clearBindings();
//                    update.statement(statement, map);
//                    if (statement.executeUpdateDelete() > 0) {
//                        count++;
//                    } else {
//                        Log.w("database", "Error update:" + statement.toString());
//                    }
//                } catch (SQLException e) {
//                    Log.e("database", "Error update " + statement.toString(), e);
//                }
//            }
//            return count;
//        });
    }

    @Override
    public long rawUpdate(@NonNull String sql, @Nullable Object[] whereArgs) {
        return statement(sql, whereArgs, statement -> {
            final long count;
            if ((count = statement.executeUpdateDelete()) <= 0) {
                Log.w("database", "删除数据出错，错误数据是:" + statement.toString());
            }
            return count;
        });
    }


    @Override
    public long rawDelete(@NonNull String sql, @Nullable Object[] whereArgs) {
        return statement(sql, whereArgs, statement -> {
            final long count;
            if ((count = statement.executeUpdateDelete()) <= 0) {
                Log.w("database", "删除数据出错，错误数据是:" + statement.toString());
            }
            return count;
        });
    }

    @Override
    public long insert(@NonNull String table, @Nullable String nullColumnHack, @NonNull Map<String, Object> values,
                       ConflictAlgorithm conflictAlgorithm) {
        SqlBuilder insert = new SqlBuilder().insert(table, values, nullColumnHack, conflictAlgorithm);
        return statement(insert.sql, insert.arguments, statement -> {
            final long count;
            if ((count = statement.executeInsert()) <= 0) {
                Log.w("database", "插入数据出错，错误数据是:" + statement.toString());
            }
            return count;
        });
    }

    @Override
    public long insert(@NonNull String table, @Nullable String nullColumnHack,
                       @NonNull List<Map<String, Object>> valueList, ConflictAlgorithm conflictAlgorithm) {
        return transaction(new Function<SQLiteDatabase, Long>() {
            @Override
            public Long apply(SQLiteDatabase input) {
                long insertSize = 0;
                for (Map<String, Object> map : valueList) {
                    insertSize += insert(table, nullColumnHack, map, conflictAlgorithm);
                }
                return insertSize;
            }
        });
//        long insertSize = 0;
//        for (Map<String, Object> map : valueList) {
//            insertSize += insert(table, nullColumnHack, map, conflictAlgorithm);
//        }
//        return insertSize;
//        final int size = valueList.size();
//        if (size == 0) {
//            Log.e("database", "Error inserting " + table + " values null");
//            return -1;
//        }
//        SqlBuilder insert = new SqlBuilder().insertList(table, valueList.get(0), nullColumnHack, conflictAlgorithm);
//        Function<SQLiteStatement<?>, Long> transaction = statement -> {
//            long count = 0L;
//            for (Map<String, Object> map : valueList) {
//                try {
//                    statement.clearBindings();
//                    new SqlBuilder().statement(statement, map);
//                    if (statement.executeInsert() > 0) {
//                        count++;
//                    } else {
//                        Log.w("database", "Error inserting:" + statement.toString());
//                    }
//                } catch (SQLException e) {
//                    Log.e("database", "Error inserting " + statement.toString(), e);
//                }
//            }
//            return count;
//        };
//        return this.transaction(insert.sql, transaction);
    }

    @Override
    public long rawInsert(@NonNull String sql, @Nullable Object[] whereArgs) {
        return statement(sql, whereArgs, statement -> {
            final long count;
            if ((count = statement.executeInsert()) <= 0) {
                Log.w("database", "插入数据出错，错误数据是:" + statement.toString());
            }
            return count;
        });
    }

    @Override
    public long statement(@NonNull String sql,
                          @NonNull Function<SQLiteStatement<?>, Long> function) {
        final SQLiteStatement<?> statement = compileStatement(sql);
        try {
            acquireReference();
            Long statementSize = function.apply(statement);
            if (statementSize == null) {
                return -1L;
            }
            return statementSize;
        } catch (SQLException e) {
            Log.e("database", "Error statement " + statement.toString(), e);
            return -1;
        } finally {
            CloseUtils.closeQuietly(statement);
            releaseReference();
        }
    }

    @Override
    public long transaction(@NonNull String sql,
                            @NonNull Function<SQLiteStatement<?>, Long> function) {
        try {
            beginTransaction();
            final long transactionSize = statement(sql, function);
            setTransactionSuccessful();
            return transactionSize;
        } finally {
            endTransaction();
        }
    }

    @Override
    public long transaction(@NonNull Function<SQLiteDatabase, Long> function) {
        try {
            beginTransaction();
            final Long transactionSize = function.apply(this);
            if (transactionSize == null) {
                return -1;
            }
            setTransactionSuccessful();
            return transactionSize;
        } finally {
            endTransaction();
        }
    }


}
