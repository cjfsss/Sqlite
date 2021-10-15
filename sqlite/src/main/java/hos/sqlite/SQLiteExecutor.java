package hos.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    boolean execSQL(@NonNull final String sql);

    boolean execSQL(@NonNull final String sql, @NonNull final Object[] bindArgs);


    @Nullable
    default List<Map<String, Object>> query(@NonNull final String table, @Nullable final String[] columns,
                                            @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final String groupBy,
                                            @Nullable final String having, @Nullable final String orderBy) {
        return query(table, false, columns, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    @Nullable
    default List<Map<String, Object>> queryAll(@NonNull final String table) {
        return query(table, false, null, "", new String[]{}, null, null, null, null, null);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String table, @Nullable final String[] columns,
                                            @NonNull final String where, @NonNull final Object[] whereArgs) {
        return query(table, false, columns, where, whereArgs, null, null, null, null, null);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String table, @NonNull final String where,
                                            @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                            @Nullable final String orderBy) {
        return query(table, false, null, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String table, @NonNull final String where,
                                            @NonNull final Object[] whereArgs) {
        return query(table, false, null, where, whereArgs, null, null, null, null, null);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String table, @NonNull final String where,
                                            @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset) {
        return query(table, false, null, where, whereArgs, null, null, null, limit, offset);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String table, @Nullable final String[] columns,
                                            @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final Integer limit,
                                            @Nullable final Integer offset) {
        return query(table, false, columns, where, whereArgs, null, null, null, limit, offset);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String table, @Nullable final String[] columns,
                                           @NonNull final String where, @NonNull final Object[] whereArgs) {
        return queryFirst(table, false, columns, where, whereArgs, null, null, null, null, null);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String table, @Nullable final String[] columns,
                                           @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final String groupBy,
                                           @Nullable final String having, @Nullable final String orderBy) {
        return queryFirst(table, false, columns, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String table, @NonNull final String where,
                                           @NonNull final Object[] whereArgs) {
        return queryFirst(table, false, null, where, whereArgs, null, null, null, null, null);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String table, @NonNull final String where,
                                           @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                           @Nullable final String orderBy) {
        return queryFirst(table, false, null, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String table, @NonNull final String where,
                                           @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset) {
        return queryFirst(table, false, null, where, whereArgs, null, null, null, limit, offset);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String table, @Nullable final String[] columns,
                                           @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final Integer limit,
                                           @Nullable final Integer offset) {
        return queryFirst(table, false, columns, where, whereArgs, null, null, null, limit, offset);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String table, final boolean distinct,
                                            @Nullable final String[] columns, @NonNull final String where, @NonNull final Object[] whereArgs,
                                            @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy,
                                            @Nullable final Integer limit, @Nullable final Integer offset) {
        final SqlBuilder sqlBuilder = new SqlBuilder()
                .query(table, distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset);
        return rawQuery(sqlBuilder.sql, sqlBuilder.whereArgs);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String table, final boolean distinct,
                                           @Nullable final String[] columns, @NonNull final String where, @NonNull final Object[] whereArgs,
                                           @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy,
                                           @Nullable final Integer limit, @Nullable final Integer offset) {
        final SqlBuilder sqlBuilder = new SqlBuilder()
                .query(table, distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset);
        return rawQueryFirst(sqlBuilder.sql, sqlBuilder.whereArgs);
    }

    @Nullable
    default Map<String, Object> rawQueryFirst(@NonNull final String sql, @Nullable final Object[] arguments) {
        final SqlBuilder sqlBuilder = new SqlBuilder().sql(sql, arguments);
        final Cursor cursor = rawQueryCursor(sql, arguments);
        if (cursor == null) {
            return null;
        }
        try {
            @NonNull final Map<String, Object> map = new HashMap<>();
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

    @Nullable
    default List<Map<String, Object>> rawQuery(@NonNull final String sql, @Nullable final Object[] arguments) {
        final Cursor cursor = rawQueryCursor(sql, arguments);
        if (cursor == null) {
            return null;
        }
        try {
            final SqlBuilder sqlBuilder = new SqlBuilder();
            @NonNull final List<Map<String, Object>> resultList = new ArrayList<>();
            while (cursor.moveToNext()) {
                @NonNull final Map<String, Object> map = new HashMap<>();
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

    @Nullable
    default Cursor queryCursorAll(@NonNull final String table) {
        return queryCursor(table, false, null, "", new String[]{}, null, null, null, null, null);
    }

    @Nullable
    default Cursor queryCursor(@NonNull final String table, @Nullable final String[] columns,
                               @NonNull final String where, @NonNull final Object[] whereArgs) {
        return queryCursor(table, false, columns, where, whereArgs, null, null, null, null, null);
    }

    @Nullable
    default Cursor queryCursor(@NonNull final String table, @NonNull final String where,
                               @NonNull final Object[] whereArgs) {
        return queryCursor(table, false, null, where, whereArgs, null, null, null, null, null);
    }

    @Nullable
    default Cursor queryCursor(@NonNull final String table, @NonNull final String where,
                               @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                               @Nullable final String orderBy) {
        return queryCursor(table, false, null, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    @Nullable
    default Cursor queryCursor(@NonNull final String table, @Nullable final String[] columns,
                               @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final String groupBy,
                               @Nullable final String having, @Nullable final String orderBy) {
        return queryCursor(table, false, columns, where, whereArgs, groupBy, having, orderBy, null, null);
    }

    @Nullable
    default Cursor queryCursor(@NonNull final String table, @NonNull final String where,
                               @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset) {
        return queryCursor(table, false, null, where, whereArgs, null, null, null, limit, offset);
    }

    @Nullable
    default Cursor queryCursor(@NonNull final String table, @Nullable final String[] columns,
                               @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final Integer limit,
                               @Nullable final Integer offset) {
        return queryCursor(table, false, columns, where, whereArgs, null, null, null, limit, offset);
    }

    @Nullable
    default Cursor queryCursor(@NonNull final String table, final boolean distinct, @Nullable final String[] columns,
                               @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final String groupBy,
                               @Nullable final String having, @Nullable final String orderBy, @Nullable final Integer limit,
                               @Nullable final Integer offset) {
        final SqlBuilder sqlBuilder = new SqlBuilder()
                .query(table, distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset);
        return rawQueryCursor(sqlBuilder.sql, sqlBuilder.whereArgs);
    }

    @Nullable
    Cursor rawQueryCursor(@NonNull final String sql, @Nullable final Object[] selectionArgs);

    int update(@NonNull final String table, @NonNull final ContentValues values, @NonNull final String whereClause,
               @Nullable final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm);

    default int update(@NonNull final String table, @NonNull final ContentValues values,
                       @NonNull final String whereClause, @Nullable final Object[] whereArgs) {
        return update(table, values, whereClause, whereArgs, ConflictAlgorithm.none);
    }

    long update(@NonNull final String table, @NonNull final Map<String, Object> values,
               @NonNull final String whereClause, @Nullable final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm);

    default long update(@NonNull final String table, @NonNull final Map<String, Object> values,
                       @NonNull final String whereClause, @Nullable final Object[] whereArgs) {
        return update(table, values, whereClause, whereArgs, ConflictAlgorithm.none);
    }

    long update(@NonNull final String table, @NonNull final List<Map<String, Object>> valueList,
               @NonNull final String whereClause, @Nullable final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm);

    default long update(@NonNull final String table, @NonNull final List<Map<String, Object>> valueList,
                       @NonNull final String whereClause, @Nullable final Object[] whereArgs) {
        return update(table, valueList, whereClause, whereArgs, ConflictAlgorithm.none);
    }

    long rawUpdate(@NonNull final String sql, @Nullable final Object[] whereArgs);

    int delete(@NonNull final String table, @Nullable final String whereClause, @Nullable final Object[] whereArgs);

    long rawDelete(@NonNull final String sql, @Nullable final Object[] whereArgs);

    long insert(@NonNull final String table, @Nullable final String nullColumnHack, @NonNull final ContentValues values,
                ConflictAlgorithm conflictAlgorithm);

    default long insert(@NonNull final String table, @Nullable final String nullColumnHack,
                        @NonNull final ContentValues values) {
        return insert(table, nullColumnHack, values, ConflictAlgorithm.none);
    }

    long insert(@NonNull final String table, @Nullable final String nullColumnHack,
                @NonNull final Map<String, Object> values, ConflictAlgorithm conflictAlgorithm);

    default long insert(@NonNull final String table, @Nullable final String nullColumnHack,
                        @NonNull final Map<String, Object> values) {
        return insert(table, nullColumnHack, values, ConflictAlgorithm.none);
    }

    long insert(@NonNull final String table, @Nullable final String nullColumnHack,
                @NonNull final List<Map<String, Object>> valueList, ConflictAlgorithm conflictAlgorithm);

    default long insert(@NonNull final String table, @Nullable final String nullColumnHack,
                        @NonNull final List<Map<String, Object>> valueList) {
        return insert(table, nullColumnHack, valueList, ConflictAlgorithm.none);
    }

    long rawInsert(@NonNull final String sql, @Nullable final Object[] whereArgs);

}
