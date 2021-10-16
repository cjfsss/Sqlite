package hos.sqlite.datebase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;

import java.util.List;
import java.util.Map;

import hos.sqlite.SQLiteExecutor;
import hos.sqlite.statement.SQLTransaction;
import hos.sqlite.statement.SQLiteDatabase;
import hos.sqlite.statement.SQLiteStatement;
import hos.utils.CloseUtils;

/**
 * <p>Title: DatabaseExecutor </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/14 13:28
 */
public interface DatabaseExecutor extends SQLiteExecutor,
        SQLTransaction {

    @NonNull
    SQLiteDatabase getConnection();

    @NonNull
    default String getLogTag() {
        return "database";
    }

    boolean tableIsExist(@NonNull final String tableName);

    @Nullable
    default String getCreateSqlForMaster(@NonNull final String tableName) {
        @Nullable final List<Map<String, Object>> sqlCreateMap = rawQuery(
                "SELECT sql FROM sqlite_master WHERE name = ? ", new Object[]{tableName});
        if (sqlCreateMap == null || sqlCreateMap.isEmpty()) {
            return null;
        }
        return (String) sqlCreateMap.get(0).get("sql");
    }

    /**
     * 获取表的主键
     *
     * @param tableName 表名
     * @return 主键
     */
    @Nullable
    default String getPrimaryKey(@NonNull final String tableName) {
        @Nullable final List<Map<String, Object>> mapList = rawQuery(" pragma table_info ( ? ) ",
                new Object[]{tableName});
        if (mapList == null || mapList.isEmpty()) {
            return null;
        }
        for (Map<String, Object> map : mapList) {
            if (TextUtils.equals(String.valueOf(map.get("pk")), "1")) {
                // 是主键
                return String.valueOf(map.get("name"));
            }
        }
        return null;
    }

    /**
     * 获取当前数据库所有表名
     */
    @Nullable
    default String[] getDBTables() {
        @NonNull final StringBuilder stringBuffer = new StringBuilder();
        Cursor cursor = null;
        try {
            cursor = rawQueryCursor("SELECT name FROM sqlite_master WHERE type=? ORDER BY name", new Object[]{"table"});
            if (cursor == null) {
                return null;
            }
            while (cursor.moveToNext()) {
                if (stringBuffer.length() != 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(cursor.getString(0));
            }
        } finally {
            CloseUtils.closeQuietly(cursor);
        }
        return stringBuffer.toString().split(",");
    }

    /**
     * 删除所有的表
     */
    default boolean dropAllTables() {
        @Nullable final String[] tables = getDBTables();
        if (tables == null || tables.length == 0) {
            return false;
        }
        final int tableSize = tables.length;
        @NonNull final SQLiteDatabase sqLiteDatabase = getConnection();
        @NonNull final String sql = "DROP TABLE IF EXISTS [?]";
        return tableSize == sqLiteDatabase.transaction(sql, statement -> {
            /* 删除数据库中所有的表 */
            long count = 0;
            for (String table : tables) {
                statement.clearBindings();
                if (table == null) {
                    continue;
                }
                statement.bindString(1, table);
                try {
                    statement.execute();
                    count++;
                } catch (SQLException e) {
                    Log.e(getLogTag(), "Error dropAllTables " + statement.toString(), e);
                }
            }
            return count;
        });
    }

    @Nullable
    default Map<String, Object> queryFirstByPrimaryKey(@NonNull final String table, @NonNull final String primaryKey,
                                                       @NonNull final Object primaryKeyValue) {
        return queryFirst(table, primaryKey + " =?", new String[]{String.valueOf(primaryKeyValue)});
    }

    @Override
    default boolean execSQL(@NonNull final String sql) {
        return getConnection().execSQL(sql);
    }

    @Override
    default boolean execSQL(@NonNull final String sql, @NonNull final Object[] bindArgs) {
        return getConnection().execSQL(sql, bindArgs);
    }

    @Nullable
    @Override
    default Cursor rawQueryCursor(@NonNull String sql, @Nullable Object[] selectionArgs) {
        return getConnection().rawQueryCursor(sql, selectionArgs);
    }

    @Override
    default int update(@NonNull String table, @NonNull ContentValues values, @NonNull String whereClause,
                       @Nullable final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().update(table, values, whereClause, whereArgs, conflictAlgorithm);
    }

    @Override
    default long update(@NonNull String table, @NonNull Map<String, Object> values, @NonNull String whereClause,
                        @Nullable Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().update(table, values, whereClause, whereArgs, conflictAlgorithm);
    }

    @Override
    default long transactionUpdate(@NonNull String table, @NonNull List<Map<String, Object>> valueList, @NonNull String whereClause,
                                   @Nullable Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().transactionUpdate(table, valueList, whereClause, whereArgs, conflictAlgorithm);
    }


    @Override
    default long transactionUpdateValue(@NonNull String table, @NonNull List<ContentValues> valueList, @NonNull String whereClause, @Nullable Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().transactionUpdateValue(table, valueList, whereClause, whereArgs, conflictAlgorithm);
    }


    @Override
    default long rawUpdate(@NonNull String sql, @Nullable Object[] whereArgs) {
        return getConnection().rawUpdate(sql, whereArgs);
    }

    @Override
    default int delete(@NonNull String table, @Nullable String whereClause, @Nullable final Object[] whereArgs) {
        return getConnection().delete(table, whereClause, whereArgs);
    }

    @Override
    default long rawDelete(@NonNull String sql, @Nullable Object[] whereArgs) {
        return getConnection().rawDelete(sql, whereArgs);
    }

    @Override
    default long insert(@NonNull String table, @Nullable final String nullColumnHack, @NonNull ContentValues values,
                        ConflictAlgorithm conflictAlgorithm) {
        return getConnection().insert(table, nullColumnHack, values, conflictAlgorithm);
    }


    @Override
    default long insert(@NonNull String table, @Nullable String nullColumnHack, @NonNull Map<String, Object> values,
                        ConflictAlgorithm conflictAlgorithm) {
        return getConnection().insert(table, nullColumnHack, values, conflictAlgorithm);
    }

    @Override
    default long transactionInsert(@NonNull String table, @Nullable String nullColumnHack,
                                   @NonNull List<Map<String, Object>> valueList, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().transactionInsert(table, nullColumnHack, valueList, conflictAlgorithm);
    }


    @Override
    default long transactionInsertValue(@NonNull String table, @Nullable String nullColumnHack, @NonNull List<ContentValues> valueList, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().transactionInsertValue(table, nullColumnHack, valueList, conflictAlgorithm);
    }

    @Override
    default long rawInsert(@NonNull String sql, @Nullable Object[] whereArgs) {
        return getConnection().rawInsert(sql, whereArgs);
    }

    @Override
    default long statement(@NonNull final String sql,
                           @NonNull final Function<SQLiteStatement<?>, Long> function) {
        return getConnection().statement(sql, function);
    }

    @Override
    default long transaction(@NonNull final String sql,
                             @NonNull final Function<SQLiteStatement<?>, Long> function) {
        return getConnection().transaction(sql, function);
    }

    @Override
    default long transaction(@NonNull Function<SQLiteDatabase, Long> function) {
        return getConnection().transaction(function);
    }

    /**
     * 删除表中数据
     *
     * @param tableNameArrays 要删除的表
     */
    default boolean deleteArrays(@NonNull final String[] tableNameArrays) {
        @NonNull final SQLiteDatabase sqLiteDatabase = getConnection();
        return 0 < sqLiteDatabase.transaction(new Function<SQLiteDatabase, Long>() {
            @Override
            public Long apply(SQLiteDatabase database) {
                long count = 0;
                for (final String tableName : tableNameArrays) {
                    try {
                        database.execSQL("DELETE FROM " + tableName);
                        count++;
                    } catch (SQLException e) {
                        Log.e(getLogTag(), "Error execSQL DELETE FROM " + tableName, e);
                        count = -1;
                    }
                }
                return count;
            }
        });
    }

    default boolean deleteByPrimaryKey(@NonNull final String tableName, @NonNull final String primaryKey,
                                       @NonNull final Object[] whereIn) {
        /* 获取主键的名称和对应的值 */
        final SqlBuilder sqlBuilder = new SqlBuilder().whereIn(tableName, primaryKey, whereIn);
        return execSQL(sqlBuilder.sql);
    }

    default long deleteByPrimaryKey(@NonNull final String tableName, @NonNull final String primaryKey,
                                    @Nullable final Object value) {
        return delete(tableName, primaryKey + " = ? ", new String[]{String.valueOf(value)});
    }

    /**
     * 删除所有的表数据
     */
    default boolean deleteAllTables() {
        @Nullable final String[] tables = getDBTables();
        if (tables == null || tables.length == 0) {
            return false;
        }
        return deleteArrays(tables);
    }
}
