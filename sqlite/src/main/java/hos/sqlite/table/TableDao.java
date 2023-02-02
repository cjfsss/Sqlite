package hos.sqlite.table;

import android.database.Cursor;




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

    
    default TABLE queryFirstTableByPrimaryKey( final Object primaryKeyValue) {
        return queryFirstTable(getPrimaryKey() + " =?", new String[]{String.valueOf(primaryKeyValue)});
    }

    
    default List<TABLE> queryTable(boolean distinct,  final String[] columns,  final String where,
                                    final Object[] whereArgs,  final String groupBy,  final String having,
                                    final String orderBy,  final Integer limit,  final Integer offset) {
        return toTableList(queryCursor(distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset));
    }

    
    default TABLE queryFirstTable(boolean distinct,  final String[] columns,  final String where,
                                   final Object[] whereArgs,  final String groupBy,  final String having,
                                   final String orderBy,  final Integer limit,  final Integer offset) {
        return toTable(queryCursor(distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset));
    }

    
    default List<TABLE> queryTable( final String[] columns,  final String where,
                                    final Object[] whereArgs) {
        return toTableList(queryCursor(columns, where, whereArgs));
    }

    
    default List<TABLE> queryTable( final String[] columns,  final String where,
                                    final Object[] whereArgs,  final String groupBy,  final String having,
                                    final String orderBy) {
        return toTableList(queryCursor(columns, where, whereArgs, groupBy, having, orderBy));
    }

    
    default List<TABLE> queryTableAll() {
        return toTableList(queryCursorAll());
    }

    
    default List<TABLE> queryTable( final String where,  final Object[] whereArgs) {
        return toTableList(queryCursor(where, whereArgs));
    }

    
    default List<TABLE> queryTable( final String where,  final Object[] whereArgs,
                                    final String groupBy,  final String having,  final String orderBy) {
        return toTableList(queryCursor(where, whereArgs, groupBy, having, orderBy));
    }

    
    default List<TABLE> queryTable( final String where,  final Object[] whereArgs,
                                    final Integer limit,  final Integer offset) {
        return toTableList(queryCursor(where, whereArgs, limit, offset));
    }

    
    default List<TABLE> queryTable( final String[] columns,  final String where,
                                    final Object[] whereArgs,  final Integer limit,  final Integer offset) {
        return toTableList(queryCursor(columns, where, whereArgs, limit, offset));
    }

    
    default TABLE queryFirstTable( final String[] columns,  final String where,
                                   final Object[] whereArgs) {
        return toTable(queryCursor(columns, where, whereArgs));
    }

    
    default TABLE queryFirstTable( final String[] columns,  final String where,
                                   final Object[] whereArgs,  final String groupBy,  final String having,
                                   final String orderBy) {
        return toTable(queryCursor(columns, where, whereArgs, groupBy, having, orderBy));
    }

    
    default TABLE queryFirstTable( final String where,  final Object[] whereArgs) {
        return toTable(queryCursor(where, whereArgs));
    }

    
    default TABLE queryFirstTable( final String where,  final Object[] whereArgs,
                                   final String groupBy,  final String having,  final String orderBy) {
        return toTable(queryCursor(where, whereArgs, groupBy, having, orderBy));
    }

    
    default TABLE queryFirstTable( final String where,  final Object[] whereArgs,
                                   final Integer limit,  final Integer offset) {
        return toTable(queryCursor(where, whereArgs, limit, offset));
    }

    
    default TABLE queryFirstTable( final String[] columns,  final String where,
                                   final Object[] whereArgs,  final Integer limit,  final Integer offset) {
        return toTable(queryCursor(columns, where, whereArgs, limit, offset));
    }
    /**
     * 保存或者更新数据
     * @param table 更新的实体
     * @param where 更新的条件sql
     * @param whereArgs 更新的条件
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate( final TABLE table,  final String where,
                                  final String[] whereArgs) {
        final Map<String, Object> values = toMap(table);
        return saveOrUpdate(values, where, whereArgs);
    }
    /**
     * 根据主键保存或者更新数据
     * @param table 更新的实体
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate( final TABLE table) {
        final Map<String, Object> values = toMap(table);
        return saveOrUpdate(values);
    }

    /**
     * 将cursor转换为实体
     */
    
    default TABLE toTable( Cursor cursor) {
         final List<TABLE> tableList = toTableList(cursor);
        if (tableList == null || tableList.isEmpty()) {
            return null;
        }
        return tableList.get(0);
    }

    /**
     * 将cursor转换为实体列表
     */
    
    List<TABLE> toTableList( Cursor cursor);
    /**
     * 将cursor转换为Map<String, Object>
     */
    
    Map<String, Object> toMap( final TABLE table);

}