package hos.sqlite;

import android.content.ContentValues;
import android.database.Cursor;




import hos.sqlite.datebase.ConflictAlgorithm;
import hos.sqlite.datebase.SqlBuilder;
import hos.utils.CloseUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Db </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/22 17:18
 */
public interface SQLiteExecutor {

    boolean execSQL( final String sql);

    boolean execSQL( final String sql,  final Object[] bindArgs);

    default List<Map<String, Object>> query( final String table,  final String[] columns,
                                             final String where,  final Object[] whereArgs,  final String groupBy,
                                             final String having,  final String orderBy) {
        return query(table, false, columns, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    
    default List<Map<String, Object>> queryAll( final String table) {
        return query(table, false, null, "", new String[]{}, null, null, null, null, null);
    }

    
    default List<Map<String, Object>> query( final String table,  final String[] columns,
                                             final String where,  final Object[] whereArgs) {
        return query(table, false, columns, where, whereArgs, null, null, null, null, null);
    }

    
    default List<Map<String, Object>> query( final String table,  final String where,
                                             final Object[] whereArgs,  final String groupBy,  final String having,
                                             final String orderBy) {
        return query(table, false, null, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    
    default List<Map<String, Object>> query( final String table,  final String where,
                                             final Object[] whereArgs) {
        return query(table, false, null, where, whereArgs, null, null, null, null, null);
    }

    
    default List<Map<String, Object>> query( final String table,  final String where,
                                             final Object[] whereArgs,  final Integer limit,  final Integer offset) {
        return query(table, false, null, where, whereArgs, null, null, null, limit, offset);
    }

    
    default List<Map<String, Object>> query( final String table,  final String[] columns,
                                             final String where,  final Object[] whereArgs,  final Integer limit,
                                             final Integer offset) {
        return query(table, false, columns, where, whereArgs, null, null, null, limit, offset);
    }

    
    default Map<String, Object> queryFirst( final String table,  final String[] columns,
                                            final String where,  final Object[] whereArgs) {
        return queryFirst(table, false, columns, where, whereArgs, null, null, null, null, null);
    }

    
    default Map<String, Object> queryFirst( final String table,  final String[] columns,
                                            final String where,  final Object[] whereArgs,  final String groupBy,
                                            final String having,  final String orderBy) {
        return queryFirst(table, false, columns, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    
    default Map<String, Object> queryFirst( final String table,  final String where,
                                            final Object[] whereArgs) {
        return queryFirst(table, false, null, where, whereArgs, null, null, null, null, null);
    }

    
    default Map<String, Object> queryFirst( final String table,  final String where,
                                            final Object[] whereArgs,  final String groupBy,  final String having,
                                            final String orderBy) {
        return queryFirst(table, false, null, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    
    default Map<String, Object> queryFirst( final String table,  final String where,
                                            final Object[] whereArgs,  final Integer limit,  final Integer offset) {
        return queryFirst(table, false, null, where, whereArgs, null, null, null, limit, offset);
    }

    
    default Map<String, Object> queryFirst( final String table,  final String[] columns,
                                            final String where,  final Object[] whereArgs,  final Integer limit,
                                            final Integer offset) {
        return queryFirst(table, false, columns, where, whereArgs, null, null, null, limit, offset);
    }

    
    default List<Map<String, Object>> query( final String table, final boolean distinct,
                                             final String[] columns,  final String where,  final Object[] whereArgs,
                                             final String groupBy,  final String having,  final String orderBy,
                                             final Integer limit,  final Integer offset) {
        final SqlBuilder sqlBuilder = new SqlBuilder()
                .query(table, distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset);
        return rawQuery(sqlBuilder.sql, sqlBuilder.whereArgs);
    }

    
    default Map<String, Object> queryFirst( final String table, final boolean distinct,
                                            final String[] columns,  final String where,  final Object[] whereArgs,
                                            final String groupBy,  final String having,  final String orderBy,
                                            final Integer limit,  final Integer offset) {
        final SqlBuilder sqlBuilder = new SqlBuilder()
                .query(table, distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset);
        return rawQueryFirst(sqlBuilder.sql, sqlBuilder.whereArgs);
    }

    
    default Map<String, Object> rawQueryFirst( final String sql,  final Object[] arguments) {
        final SqlBuilder sqlBuilder = new SqlBuilder().sql(sql, arguments);
        final Cursor cursor = rawQueryCursor(sql, arguments);
        if (cursor == null) {
            return null;
        }
        try {
             final Map<String, Object> map = new HashMap<>();
            if (cursor.moveToNext()) {
                int columnCount = cursor.getColumnCount();
                for (int j = 0; j < columnCount; j++) {
                    map.put(cursor.getColumnName(j), sqlBuilder.getValue(cursor, j));
                }
            }
            return map;
        } finally {
            CloseUtils.closeQuietly(cursor);
        }
    }

    
    default List<Map<String, Object>> rawQuery( final String sql,  final Object[] arguments) {
        final Cursor cursor = rawQueryCursor(sql, arguments);
        if (cursor == null) {
            return null;
        }
        try {
            final SqlBuilder sqlBuilder = new SqlBuilder();
             final List<Map<String, Object>> resultList = new ArrayList<>();
            while (cursor.moveToNext()) {
                 final Map<String, Object> map = new HashMap<>();
                int columnCount = cursor.getColumnCount();
                for (int j = 0; j < columnCount; j++) {
                    map.put(cursor.getColumnName(j), sqlBuilder.getValue(cursor, j));
                }
                resultList.add(map);
            }
            return resultList;
        } finally {
            CloseUtils.closeQuietly(cursor);
        }
    }

    
    default Cursor queryCursorAll( final String table) {
        return queryCursor(table, false, null, "", new String[]{}, null, null, null, null, null);
    }

    
    default Cursor queryCursor( final String table,  final String[] columns,
                                final String where,  final Object[] whereArgs) {
        return queryCursor(table, false, columns, where, whereArgs, null, null, null, null, null);
    }

    
    default Cursor queryCursor( final String table,  final String where,
                                final Object[] whereArgs) {
        return queryCursor(table, false, null, where, whereArgs, null, null, null, null, null);
    }

    
    default Cursor queryCursor( final String table,  final String where,
                                final Object[] whereArgs,  final String groupBy,  final String having,
                                final String orderBy) {
        return queryCursor(table, false, null, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    
    default Cursor queryCursor( final String table,  final String[] columns,
                                final String where,  final Object[] whereArgs,  final String groupBy,
                                final String having,  final String orderBy) {
        return queryCursor(table, false, columns, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    
    default Cursor queryCursor( final String table,  final String where,
                                final Object[] whereArgs,  final Integer limit,  final Integer offset) {
        return queryCursor(table, false, null, where, whereArgs, null, null, null, limit, offset);
    }

    
    default Cursor queryCursor( final String table,  final String[] columns,
                                final String where,  final Object[] whereArgs,  final Integer limit,
                                final Integer offset) {
        return queryCursor(table, false, columns, where, whereArgs, null, null, null, limit, offset);
    }

    
    default Cursor queryCursor( final String table, final boolean distinct,  final String[] columns,
                                final String where,  final Object[] whereArgs,  final String groupBy,
                                final String having,  final String orderBy,  final Integer limit,
                                final Integer offset) {
        final SqlBuilder sqlBuilder = new SqlBuilder()
                .query(table, distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset);
        return rawQueryCursor(sqlBuilder.sql, sqlBuilder.whereArgs);
    }

    
    Cursor rawQueryCursor( final String sql,  final Object[] selectionArgs);

    int update( final String table,  final ContentValues values,  final String whereClause,
                final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm);

    default int update( final String table,  final ContentValues values,
                        final String whereClause,  final Object[] whereArgs) {
        return update(table, values, whereClause, whereArgs, ConflictAlgorithm.none);
    }

    long update( final String table,  final Map<String, Object> values,
                 final String whereClause,  final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm);

    default long update( final String table,  final Map<String, Object> values,
                         final String whereClause,  final Object[] whereArgs) {
        return update(table, values, whereClause, whereArgs, ConflictAlgorithm.none);
    }

    long transactionUpdateValue( final String table,  final List<ContentValues> values,  final String whereClause,
                                 final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm);

    default long transactionUpdateValue( final String table,  final List<ContentValues> values,
                                         final String whereClause,  final Object[] whereArgs) {
        return transactionUpdateValue(table, values, whereClause, whereArgs, ConflictAlgorithm.none);
    }

    long transactionUpdate( final String table,  final List<Map<String, Object>> valueList,
                            final String whereClause,  final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm);

    default long transactionUpdate( final String table,  final List<Map<String, Object>> valueList,
                                    final String whereClause,  final Object[] whereArgs) {
        return transactionUpdate(table, valueList, whereClause, whereArgs, ConflictAlgorithm.none);
    }

    long rawUpdate( final String sql,  final Object[] whereArgs);

    int delete( final String table,  final String whereClause,  final Object[] whereArgs);

    long rawDelete( final String sql,  final Object[] whereArgs);

    long insert( final String table,  final String nullColumnHack,  final ContentValues values,
                ConflictAlgorithm conflictAlgorithm);

    default long insert( final String table,  final String nullColumnHack,
                         final ContentValues values) {
        return insert(table, nullColumnHack, values, ConflictAlgorithm.none);
    }


    long transactionInsertValue( final String table,  final String nullColumnHack,  final List<ContentValues> values,
                                ConflictAlgorithm conflictAlgorithm);

    default long transactionInsertValue( final String table,  final String nullColumnHack,
                                         final List<ContentValues> values) {
        return transactionInsertValue(table, nullColumnHack, values, ConflictAlgorithm.none);
    }

    long insert( final String table,  final String nullColumnHack,
                 final Map<String, Object> values, ConflictAlgorithm conflictAlgorithm);

    default long insert( final String table,  final String nullColumnHack,
                         final Map<String, Object> values) {
        return insert(table, nullColumnHack, values, ConflictAlgorithm.none);
    }

    long transactionInsert( final String table,  final String nullColumnHack,
                            final List<Map<String, Object>> valueList, ConflictAlgorithm conflictAlgorithm);

    default long transactionInsert( final String table,  final String nullColumnHack,
                                    final List<Map<String, Object>> valueList) {
        return transactionInsert(table, nullColumnHack, valueList, ConflictAlgorithm.none);
    }

    long rawInsert( final String sql,  final Object[] whereArgs);

}
