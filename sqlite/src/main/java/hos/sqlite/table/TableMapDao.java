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

    @NonNull
    String getTableName();

    /**
     * 获取表的主键,最后复写该方法
     *
     * @return 主键
     */
    @NonNull
    String getPrimaryKey();

    @NonNull
    default String getCreateTableSql() {
        return "CREATE TABLE IF NOT EXISTS '" + getTableName() + "' ";
    }

    @NonNull
    default String getCreateTablePrimaryKeySql() {
        return " '" + getPrimaryKey() + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ";
    }

    default boolean isExist() {
        return getDatabase().tableIsExist(getTableName());
    }

    void createTable(@NonNull final String createTableSql, @NonNull final String createTablePrimaryKeySql);

    default void checkTableExist() {
        if (!isExist()) {
            createTable(getCreateTableSql(), getCreateTablePrimaryKeySql());
        }
    }

    @Nullable
    default String getCreateSqlForMaster() {
        checkTableExist();
        return getDatabase().getCreateSqlForMaster(getTableName());
    }

    default boolean execute(@NonNull String sql) {
        checkTableExist();
        return getDatabase().execSQL(sql);
    }

    default boolean execute(@NonNull final String sql, @NonNull final Object[] arguments) {
        checkTableExist();
        return getDatabase().execSQL(sql, arguments);
    }

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
        return getDatabase().insert(getTableName(), null, values, conflictAlgorithm);
    }

    default long insert(@NonNull final Map<String, Object> values, @NonNull final ConflictAlgorithm conflictAlgorithm) {
        checkTableExist();
        return getDatabase().insert(getTableName(), null, values, conflictAlgorithm);
    }

    default long insert(@NonNull final List<Map<String, Object>> values) {
        checkTableExist();
        return getDatabase().insert(getTableName(), null, values);
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

    default long clearTableData() {
        checkTableExist();
        return getDatabase().delete(getTableName(), null, null);
    }

    default boolean deleteByPrimaryKey(@NonNull final Object[] whereIn) {
        checkTableExist();
        return getDatabase().deleteByPrimaryKey(getTableName(), getPrimaryKey(), whereIn);
    }

    default long deleteByPrimaryKey(@NonNull final Object primaryKeyValue) {
        checkTableExist();
        return getDatabase().deleteByPrimaryKey(getTableName(), getPrimaryKey(), primaryKeyValue);
    }

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

    default boolean saveOrUpdate(@NonNull final Map<String, Object> map) {
        String primaryValue = String.valueOf(map.get(getPrimaryKey()));
        if (TextUtils.isEmpty(primaryValue)) {
            return false;
        }
        return saveOrUpdate(map, " " + getPrimaryKey() + "=? ", new String[]{primaryValue});
    }

    void beforeInsert(@NonNull final Map<String, Object> mapValues);

    default void beforeUpdate(@NonNull final Map<String, Object> mapValues, @NonNull final Map<String, Object> oldMap) {
        mapValues.put(getPrimaryKey(), oldMap.get(getPrimaryKey()));
    }
}
