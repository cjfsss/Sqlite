package hos.sqlite.datebase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;
import android.util.Log;


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

    SQLiteDatabase getConnection();

    
    default String getLogTag() {
        return "database";
    }

    boolean tableIsExist( final String tableName);

    
    default String getCreateSqlForMaster( final String tableName) {
         final List<Map<String, Object>> sqlCreateMap = rawQuery(
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
    
    default String getPrimaryKey( final String tableName) {
         final List<Map<String, Object>> mapList = rawQuery(" pragma table_info ( ? ) ",
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
    
    default String[] getDBTables() {
         final StringBuilder stringBuffer = new StringBuilder();
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
         final String[] tables = getDBTables();
        if (tables == null || tables.length == 0) {
            return false;
        }
        final int tableSize = tables.length;
         final SQLiteDatabase sqLiteDatabase = getConnection();
         final String sql = "DROP TABLE IF EXISTS [?]";
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

    
    default Map<String, Object> queryFirstByPrimaryKey( final String table,  final String primaryKey,
                                                        final Object primaryKeyValue) {
        return queryFirst(table, primaryKey + " =?", new String[]{String.valueOf(primaryKeyValue)});
    }

    @Override
    default boolean execSQL( final String sql) {
        return getConnection().execSQL(sql);
    }

    @Override
    default boolean execSQL( final String sql,  final Object[] bindArgs) {
        return getConnection().execSQL(sql, bindArgs);
    }

    
    @Override
    default Cursor rawQueryCursor( String sql,  Object[] selectionArgs) {
        return getConnection().rawQueryCursor(sql, selectionArgs);
    }

    @Override
    default int update( String table,  ContentValues values,  String whereClause,
                        final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().update(table, values, whereClause, whereArgs, conflictAlgorithm);
    }

    @Override
    default long update( String table,  Map<String, Object> values,  String whereClause,
                         Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().update(table, values, whereClause, whereArgs, conflictAlgorithm);
    }

    @Override
    default long transactionUpdate( String table,  List<Map<String, Object>> valueList,  String whereClause,
                                    Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().transactionUpdate(table, valueList, whereClause, whereArgs, conflictAlgorithm);
    }


    @Override
    default long transactionUpdateValue( String table,  List<ContentValues> valueList,  String whereClause,  Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().transactionUpdateValue(table, valueList, whereClause, whereArgs, conflictAlgorithm);
    }


    @Override
    default long rawUpdate( String sql,  Object[] whereArgs) {
        return getConnection().rawUpdate(sql, whereArgs);
    }

    @Override
    default int delete( String table,  String whereClause,  final Object[] whereArgs) {
        return getConnection().delete(table, whereClause, whereArgs);
    }

    @Override
    default long rawDelete( String sql,  Object[] whereArgs) {
        return getConnection().rawDelete(sql, whereArgs);
    }

    @Override
    default long insert( String table,  final String nullColumnHack,  ContentValues values,
                        ConflictAlgorithm conflictAlgorithm) {
        return getConnection().insert(table, nullColumnHack, values, conflictAlgorithm);
    }


    @Override
    default long insert( String table,  String nullColumnHack,  Map<String, Object> values,
                        ConflictAlgorithm conflictAlgorithm) {
        return getConnection().insert(table, nullColumnHack, values, conflictAlgorithm);
    }

    @Override
    default long transactionInsert( String table,  String nullColumnHack,
                                    List<Map<String, Object>> valueList, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().transactionInsert(table, nullColumnHack, valueList, conflictAlgorithm);
    }


    @Override
    default long transactionInsertValue( String table,  String nullColumnHack,  List<ContentValues> valueList, ConflictAlgorithm conflictAlgorithm) {
        return getConnection().transactionInsertValue(table, nullColumnHack, valueList, conflictAlgorithm);
    }

    @Override
    default long rawInsert( String sql,  Object[] whereArgs) {
        return getConnection().rawInsert(sql, whereArgs);
    }

    @Override
    default long statement( final String sql,
                            final Function<SQLiteStatement<?>, Long> function) {
        return getConnection().statement(sql, function);
    }

    @Override
    default long transaction( final String sql,
                              final Function<SQLiteStatement<?>, Long> function) {
        return getConnection().transaction(sql, function);
    }

    @Override
    default long transaction( Function<SQLiteDatabase, Long> function) {
        return getConnection().transaction(function);
    }

    /**
     * 删除表中数据
     *
     * @param tableNameArrays 要删除的表
     */
    default boolean deleteArrays( final String[] tableNameArrays) {
         final SQLiteDatabase sqLiteDatabase = getConnection();
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

    default boolean deleteByPrimaryKey( final String tableName,  final String primaryKey,
                                        final Object[] whereIn) {
        /* 获取主键的名称和对应的值 */
        final SqlBuilder sqlBuilder = new SqlBuilder().whereIn(tableName, primaryKey, whereIn);
        return execSQL(sqlBuilder.sql);
    }

    default long deleteByPrimaryKey( final String tableName,  final String primaryKey,
                                     final Object value) {
        return delete(tableName, primaryKey + " = ? ", new String[]{String.valueOf(value)});
    }

    /**
     * 删除所有的表数据
     */
    default boolean deleteAllTables() {
         final String[] tables = getDBTables();
        if (tables == null || tables.length == 0) {
            return false;
        }
        return deleteArrays(tables);
    }
}
