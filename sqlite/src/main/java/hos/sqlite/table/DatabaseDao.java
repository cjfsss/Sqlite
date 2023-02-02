package hos.sqlite.table;

import android.database.Cursor;




import hos.sqlite.DatabaseManger;
import hos.sqlite.datebase.Database;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: DataBaseDao </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/7/1 17:31
 */
public interface DatabaseDao {
    /**
     * 分页查询
     * @param pageSize 一共几页
     * @param pageIndex 第几页
     * @return 分页sql
     */
    
    default String getPage(int pageSize, int pageIndex) {
        return " LIMIT " + pageSize + " OFFSET " + pageIndex;
    }

    /**
     * 获取Database
     * @return Database
     */
    
    default Database getDatabase() {
        return DatabaseManger.getDatabase();
    }

    /**
     * 判断数据库是否存在
     * @param tableName 表名
     * @return true 数据库存在
     */
    default boolean isExist( final String tableName) {
        return getDatabase().tableIsExist(tableName);
    }

    /**
     * 获取数据表语句
     * @param tableName 表名
     * @return 表语句
     */
    
    default String getCreateSqlForMaster( final String tableName) {
        return getDatabase().getCreateSqlForMaster(tableName);
    }

    /**
     * 执行sql语句
     * @param sql 需要执行的语句
     * @return true 成功
     */
    default boolean execute( String sql) {
        return getDatabase().execSQL(sql);
    }

    /**
     * 执行sql语句
     * @param sql 需要执行的语句
     * @param arguments 条件
     * @return true 成功
     */
    default boolean execute( final String sql,  final Object[] arguments) {
        return getDatabase().execSQL(sql, arguments);
    }

    /**
     * 更新数据
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default long rawUpdate( final String sql) {
        return getDatabase().rawUpdate(sql, null);
    }

    /**
     * 更新数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default long rawUpdate( final String sql,  final Object[] whereArgs) {
        return getDatabase().rawUpdate(sql, whereArgs);
    }

    /**
     * 删除数据
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default long rawDelete( final String sql) {
        return getDatabase().rawDelete(sql, null);
    }
    /**
     * 删除数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default long rawDelete( final String sql,  final Object[] whereArgs) {
        return getDatabase().rawDelete(sql, whereArgs);
    }
    /**
     * 插入数据
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default long rawInsert( final String sql) {
        return getDatabase().rawInsert(sql, null);
    }
    /**
     * 插入数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default long rawInsert( final String sql,  final Object[] whereArgs) {
        return getDatabase().rawInsert(sql, whereArgs);
    }

    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    
    default List<Map<String, Object>> rawQuery( final String sql) {
        return getDatabase().rawQuery(sql, null);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param arguments 条件
     * @return 返回的结果
     */
    
    default List<Map<String, Object>> rawQuery( final String sql,  final Object[] arguments) {
        return getDatabase().rawQuery(sql, arguments);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    
    default Map<String, Object> rawQueryFirst( final String sql) {
        return getDatabase().rawQueryFirst(sql, null);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param arguments 条件
     * @return 返回的结果
     */
    
    default Map<String, Object> rawQueryFirst( final String sql,  final Object[] arguments) {
        return getDatabase().rawQueryFirst(sql, arguments);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    
    default Cursor rawQueryCursor( final String sql) {
        return getDatabase().rawQueryCursor(sql, null);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param selectionArgs 条件
     * @return 返回的结果
     */
    
    default Cursor rawQueryCursor( final String sql,  final Object[] selectionArgs) {
        return getDatabase().rawQueryCursor(sql, selectionArgs);
    }
    /**
     * 执行sel语句
     * @param sql 需要查询的语句
     * @return true 成功
     */
    default boolean execSQL( final String sql) {
        return getDatabase().execSQL(sql);
    }
    /**
     * 执行sel语句
     * @param sql 需要查询的语句
     * @param bindArgs 条件
     * @return true 成功
     */
    default boolean execSQL( final String sql,  final Object[] bindArgs) {
        return getDatabase().execSQL(sql, bindArgs);
    }

}
