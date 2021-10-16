package hos.sqlite.table;

import android.database.Cursor;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hos.sqlite.datebase.ConflictAlgorithm;
import hos.sqlite.datebase.SqlBuilder;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: BaseTable </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/18 13:49
 */

public interface TableMapDao extends DatabaseDao {
    /**
     * 表名
     * @return 表名
     */
    @NonNull
    String getTableName();

    /**
     * 获取表的主键,最后复写该方法
     *
     * @return 主键
     */
    @NonNull
    String getPrimaryKey();

    /**
     * 获取创建表语句
     * @return 创建表语句
     */
    @NonNull
    default String getCreateTableSql() {
        return "CREATE TABLE IF NOT EXISTS '" + getTableName() + "' ";
    }

    /**
     * 主键
     */
    @NonNull
    default String getCreateTablePrimaryKeySql() {
        return " '" + getPrimaryKey() + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ";
    }

    /**
     * 判断数据表是否存在
     * @return true 存在
     */
    default boolean isExist() {
        return getDatabase().tableIsExist(getTableName());
    }

    /**
     * 创建表
     * @param createTableSql 创建sql
     * @param createTablePrimaryKeySql 主键sql
     */
    void createTable(@NonNull final String createTableSql, @NonNull final String createTablePrimaryKeySql);

    /**
     * 检查表是否存在，不存在则创建
     */
    default void checkTableExist() {
        if (!isExist()) {
            createTable(getCreateTableSql(), getCreateTablePrimaryKeySql());
        }
    }

    /**
     * 获取表的创建语句
     * @return 表的创建语句
     */
    @Nullable
    default String getCreateSqlForMaster() {
        checkTableExist();
        return getDatabase().getCreateSqlForMaster(getTableName());
    }

    /**
     *  执行sql语句
     * @param sql 需要执行的语句
     * @return true 执行成功
     */
    default boolean execute(@NonNull String sql) {
        checkTableExist();
        return getDatabase().execSQL(sql);
    }
    /**
     * 执行sql语句
     * @param sql 需要执行的语句
     * @param arguments 条件
     * @return true 成功
     */
    default boolean execute(@NonNull final String sql, @NonNull final Object[] arguments) {
        checkTableExist();
        return getDatabase().execSQL(sql, arguments);
    }

    /**
     * 根据主键查询
     * @param primaryKeyValue 主键条件
     * @return 查询结果
     */
    @Nullable
    default Map<String, Object> queryFirstByPrimaryKey(@NonNull final Object primaryKeyValue) {
        checkTableExist();
        return getDatabase().queryFirstByPrimaryKey(getTableName(), getPrimaryKey(), primaryKeyValue);
    }

    @Nullable
    default List<Map<String, Object>> query(boolean distinct, @Nullable final String[] columns,
                                            @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final String groupBy,
                                            @Nullable final String having, @Nullable final String orderBy, @Nullable final Integer limit,
                                            @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase()
                .query(getTableName(), distinct, columns, where, whereArgs, groupBy, having, orderBy, limit, offset);
    }

    @Nullable
    default Map<String, Object> queryFirst(boolean distinct, @Nullable final String[] columns, String where,
                                           Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                           @Nullable final String orderBy, @Nullable final Integer limit, @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase()
                .queryFirst(getTableName(), distinct, columns, where, whereArgs, groupBy, having, orderBy, limit,
                        offset);
    }

    /**
     * 查询全部
     * @return 查询结果
     */
    @Nullable
    default List<Map<String, Object>> queryAll() {
        checkTableExist();
        return getDatabase().queryAll(getTableName());
    }

    @Nullable
    default List<Map<String, Object>> query(@Nullable final String[] columns, @NonNull final String where,
                                            @NonNull final Object[] whereArgs) {
        checkTableExist();
        return getDatabase().query(getTableName(), columns, where, whereArgs);
    }

    @Nullable
    default List<Map<String, Object>> query(@Nullable final String[] columns, @NonNull final String where,
                                            @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                            @Nullable final String orderBy) {
        checkTableExist();
        return getDatabase().query(getTableName(), columns, where, whereArgs, groupBy, having, orderBy);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String where, @NonNull final Object[] whereArgs) {
        checkTableExist();
        return getDatabase().query(getTableName(), where, whereArgs);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String where, @NonNull final Object[] whereArgs,
                                            @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy) {
        checkTableExist();
        return getDatabase().query(getTableName(), where, whereArgs, groupBy, having, orderBy);
    }

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String where, @NonNull final Object[] whereArgs,
                                            @Nullable final Integer limit, @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase().query(getTableName(), where, whereArgs, limit, offset);
    }

    @Nullable
    default List<Map<String, Object>> query(@Nullable final String[] columns, @NonNull final String where,
                                            @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase().query(getTableName(), columns, where, whereArgs, limit, offset);
    }

    @Nullable
    default Map<String, Object> queryFirst(@Nullable final String[] columns, @NonNull final String where,
                                           @NonNull final Object[] whereArgs) {
        checkTableExist();
        return getDatabase().queryFirst(getTableName(), columns, where, whereArgs);
    }

    @Nullable
    default Map<String, Object> queryFirst(@Nullable final String[] columns, @NonNull final String where,
                                           @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                           @Nullable final String orderBy) {
        checkTableExist();
        return getDatabase().queryFirst(getTableName(), columns, where, whereArgs, groupBy, having, orderBy);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String where, @NonNull final Object[] whereArgs) {
        checkTableExist();
        return getDatabase().queryFirst(getTableName(), where, whereArgs);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String where, @NonNull final Object[] whereArgs,
                                           @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy) {
        checkTableExist();
        return getDatabase().queryFirst(getTableName(), where, whereArgs, groupBy, having, orderBy);
    }

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String where, @NonNull final Object[] whereArgs,
                                           @Nullable final Integer limit, @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase().queryFirst(getTableName(), where, whereArgs, limit, offset);
    }

    @Nullable
    default Map<String, Object> queryFirst(@Nullable final String[] columns, @NonNull final String where,
                                           @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase().queryFirst(getTableName(), columns, where, whereArgs, limit, offset);
    }

    @Nullable
    default Cursor queryCursorAll() {
        checkTableExist();
        return getDatabase().queryCursorAll(getTableName());
    }

    @Nullable
    default Cursor queryCursor(@NonNull final String where, @NonNull final Object[] whereArgs) {
        checkTableExist();
        return getDatabase().queryCursor(getTableName(), where, whereArgs);
    }

    default Cursor queryCursor(@Nullable final String[] columns, @NonNull final String where,
                               @NonNull final Object[] whereArgs) {
        checkTableExist();
        return getDatabase().queryCursor(getTableName(), columns, where, whereArgs);
    }

    default Cursor queryCursor(@NonNull final String where, @NonNull final Object[] whereArgs,
                               @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy) {
        checkTableExist();
        return getDatabase().queryCursor(getTableName(), where, whereArgs, groupBy, having, orderBy);
    }

    default Cursor queryCursor(@Nullable final String[] columns, @NonNull final String where,
                               @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                               @Nullable final String orderBy) {
        checkTableExist();
        return getDatabase().queryCursor(getTableName(), columns, where, whereArgs, groupBy, having, orderBy);
    }

    default Cursor queryCursor(@NonNull final String where, @NonNull final Object[] whereArgs,
                               @Nullable final Integer limit, @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase().queryCursor(getTableName(), where, whereArgs, limit, offset);
    }

    default Cursor queryCursor(@Nullable final String[] columns, @NonNull final String where,
                               @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase().queryCursor(getTableName(), columns, where, whereArgs, limit, offset);
    }

    default Cursor queryCursor(boolean distinct, @Nullable final String[] columns, @NonNull final String where,
                               @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                               @Nullable final String orderBy, @Nullable final Integer limit, @Nullable final Integer offset) {
        checkTableExist();
        return getDatabase()
                .queryCursor(getTableName(), distinct, columns, where, whereArgs, groupBy, having, orderBy, limit,
                        offset);
    }

    default long insert(@NonNull final List<Map<String, Object>> values,
                        @NonNull final ConflictAlgorithm conflictAlgorithm) {
        checkTableExist();
        return getDatabase().transactionInsert(getTableName(), null, values, conflictAlgorithm);
    }

    default long insert(@NonNull final Map<String, Object> values, @NonNull final ConflictAlgorithm conflictAlgorithm) {
        checkTableExist();
        return getDatabase().insert(getTableName(), null, values, conflictAlgorithm);
    }

    default long insert(@NonNull final List<Map<String, Object>> values) {
        checkTableExist();
        return getDatabase().transactionInsert(getTableName(), null, values);
    }


    default long insert(@NonNull final Map<String, Object> values) {
        checkTableExist();
        return getDatabase().insert(getTableName(), null, values);
    }

    default long update(@NonNull final Map<String, Object> values, @NonNull final String where,
                        @NonNull final Object[] whereArgs, @NonNull final ConflictAlgorithm conflictAlgorithm) {
        checkTableExist();
        return getDatabase().update(getTableName(), values, where, whereArgs, conflictAlgorithm);
    }

    default long update(@NonNull final Map<String, Object> values, @NonNull final String where,
                        @NonNull final Object[] whereArgs) {
        checkTableExist();
        return getDatabase().update(getTableName(), values, where, whereArgs);
    }

    default long delete(@NonNull final String where, @NonNull final Object[] whereArgs) {
        checkTableExist();
        return getDatabase().delete(getTableName(), where, whereArgs);
    }

    /**
     * 清空表的数据
     * @return true 清空的条数
     */
    default long clearTableData() {
        checkTableExist();
        return getDatabase().delete(getTableName(), null, null);
    }

    /**
     * 根据主键删除数据
     * @param whereIn 删除条件
     * @return true 删除成功
     */
    default boolean deleteByPrimaryKey(@NonNull final Object[] whereIn) {
        checkTableExist();
        return getDatabase().deleteByPrimaryKey(getTableName(), getPrimaryKey(), whereIn);
    }
    /**
     * 根据主键删除数据
     * @param primaryKeyValue 删除条件
     * @return 删除条数
     */
    default long deleteByPrimaryKey(@NonNull final Object primaryKeyValue) {
        checkTableExist();
        return getDatabase().deleteByPrimaryKey(getTableName(), getPrimaryKey(), primaryKeyValue);
    }

    /**
     * 保存或者更新数据
     * @param values 更新的数据
     * @param where 更新的条件sql
     * @param whereArgs 更新的条件
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final Map<String, Object> values, @NonNull final String where,
                                 @NonNull final String[] whereArgs) {
        final String createSqlForMaster = getCreateSqlForMaster();
        final Map<String, Object> oldMap = queryFirst(where, whereArgs);
        if (oldMap != null && !oldMap.isEmpty()) {
            final SqlBuilder sqlBuilder = new SqlBuilder().mergeMap(oldMap, values, createSqlForMaster);
            beforeUpdate(sqlBuilder.mapValues, oldMap);
            return update(sqlBuilder.mapValues, where, whereArgs) > 0;
        } else {
            final SqlBuilder sqlBuilder = new SqlBuilder().filterMap(values, createSqlForMaster);
            beforeInsert(sqlBuilder.mapValues);
            return insert(sqlBuilder.mapValues) > 0;
        }
    }
    /**
     * 根据主键保存或者更新数据
     * @param map 更新的数据
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final Map<String, Object> map) {
        String primaryValue = String.valueOf(map.get(getPrimaryKey()));
        if (TextUtils.isEmpty(primaryValue)) {
            return false;
        }
        return saveOrUpdate(map, " " + getPrimaryKey() + "=? ", new String[]{primaryValue});
    }

    /**
     * 插入数据之前
     * @param mapValues 插入的数据
     */
    void beforeInsert(@NonNull final Map<String, Object> mapValues);

    /**
     * 更新数据之前
     * @param mapValues 要更新的数据
     * @param oldMap 之前的数据
     */
    default void beforeUpdate(@NonNull final Map<String, Object> mapValues, @NonNull final Map<String, Object> oldMap) {
        mapValues.put(getPrimaryKey(), oldMap.get(getPrimaryKey()));
    }
}
