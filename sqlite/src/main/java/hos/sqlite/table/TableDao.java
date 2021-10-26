package hos.sqlite.table;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: SQLTableManager </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/18 14:27
 */
public interface TableDao<TABLE> extends TableMapSaveDao {

    @Nullable
    default TABLE queryFirstTableByPrimaryKey(@NonNull final Object primaryKeyValue) {
        return queryFirstTable(getPrimaryKey() + " =?", new String[]{String.valueOf(primaryKeyValue)});
    }

    @Nullable
    default List<TABLE> queryTable(boolean distinct, @Nullable final String[] columns, @NonNull final String where,
                                   @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                   @Nullable final String orderBy, @Nullable final Integer limit, @Nullable final Integer offset) {
        return toTableList(queryCursor(distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset));
    }

    @Nullable
    default TABLE queryFirstTable(boolean distinct, @Nullable final String[] columns, @NonNull final String where,
                                  @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                  @Nullable final String orderBy, @Nullable final Integer limit, @Nullable final Integer offset) {
        return toTable(queryCursor(distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset));
    }

    @Nullable
    default List<TABLE> queryTable(@Nullable final String[] columns, @NonNull final String where,
                                   @NonNull final Object[] whereArgs) {
        return toTableList(queryCursor(columns, where, whereArgs));
    }

    @Nullable
    default List<TABLE> queryTable(@Nullable final String[] columns, @NonNull final String where,
                                   @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                   @Nullable final String orderBy) {
        return toTableList(queryCursor(columns, where, whereArgs, groupBy, having, orderBy));
    }

    @Nullable
    default List<TABLE> queryTableAll() {
        return toTableList(queryCursorAll());
    }

    @Nullable
    default List<TABLE> queryTable(@NonNull final String where, @NonNull final Object[] whereArgs) {
        return toTableList(queryCursor(where, whereArgs));
    }

    @Nullable
    default List<TABLE> queryTable(@NonNull final String where, @NonNull final Object[] whereArgs,
                                   @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy) {
        return toTableList(queryCursor(where, whereArgs, groupBy, having, orderBy));
    }

    @Nullable
    default List<TABLE> queryTable(@NonNull final String where, @NonNull final Object[] whereArgs,
                                   @Nullable final Integer limit, @Nullable final Integer offset) {
        return toTableList(queryCursor(where, whereArgs, limit, offset));
    }

    @Nullable
    default List<TABLE> queryTable(@Nullable final String[] columns, @NonNull final String where,
                                   @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset) {
        return toTableList(queryCursor(columns, where, whereArgs, limit, offset));
    }

    @Nullable
    default TABLE queryFirstTable(@Nullable final String[] columns, @NonNull final String where,
                                  @NonNull final Object[] whereArgs) {
        return toTable(queryCursor(columns, where, whereArgs));
    }

    @Nullable
    default TABLE queryFirstTable(@Nullable final String[] columns, @NonNull final String where,
                                  @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                  @Nullable final String orderBy) {
        return toTable(queryCursor(columns, where, whereArgs, groupBy, having, orderBy));
    }

    @Nullable
    default TABLE queryFirstTable(@NonNull final String where, @NonNull final Object[] whereArgs) {
        return toTable(queryCursor(where, whereArgs));
    }

    @Nullable
    default TABLE queryFirstTable(@NonNull final String where, @NonNull final Object[] whereArgs,
                                  @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy) {
        return toTable(queryCursor(where, whereArgs, groupBy, having, orderBy));
    }

    @Nullable
    default TABLE queryFirstTable(@NonNull final String where, @NonNull final Object[] whereArgs,
                                  @Nullable final Integer limit, @Nullable final Integer offset) {
        return toTable(queryCursor(where, whereArgs, limit, offset));
    }

    @Nullable
    default TABLE queryFirstTable(@Nullable final String[] columns, @NonNull final String where,
                                  @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset) {
        return toTable(queryCursor(columns, where, whereArgs, limit, offset));
    }
    /**
     * 保存或者更新数据
     * @param table 更新的实体
     * @param where 更新的条件sql
     * @param whereArgs 更新的条件
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final TABLE table, @NonNull final String where,
                                 @NonNull final String[] whereArgs) {
        final Map<String, Object> values = toMap(table);
        return saveOrUpdate(values, where, whereArgs);
    }
    /**
     * 根据主键保存或者更新数据
     * @param table 更新的实体
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final TABLE table) {
        final Map<String, Object> values = toMap(table);
        return saveOrUpdate(values);
    }

    /**
     * 将cursor转换为实体
     */
    @Nullable
    default TABLE toTable(@Nullable Cursor cursor) {
        @Nullable final List<TABLE> tableList = toTableList(cursor);
        if (tableList == null || tableList.isEmpty()) {
            return null;
        }
        return tableList.get(0);
    }

    /**
     * 将cursor转换为实体列表
     */
    @Nullable
    List<TABLE> toTableList(@Nullable Cursor cursor);
    /**
     * 将cursor转换为Map<String, Object>
     */
    @NonNull
    Map<String, Object> toMap(@NonNull final TABLE table);

}