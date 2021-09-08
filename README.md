
<p align="center"><strong>Sqlite</strong></p>


* #####  加载已经存在的数据库

```kotlin
    // 初始化数据库
    DatabaseManger.setConfig(
        DatabaseConfig.newBuilder().databasePath(
            activity(),
            PathManager.getDatabasePath(App.getApp().getDatabaseFileName())
        ).build()
    )
```

* #####  加载新的数据库

```kotlin
    // 创建数据库
    class DatabaseHelper(context: Context?) :
        SQLiteOpenHelper(context, "demo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
    // 载入数据库
    DatabaseManger.setConfig(DatabaseConfig.newBuilder()
            .setSqLiteDatabase(mDatabaseHelper.writableDatabase)
            .build())
}
```

* #####  使用  需要集成TableMapDao或者TableDao，直接调用内部方法就可以了


* ##### DatabaseDao

```java

    /**
     * 分页查询
     * @param pageSize 一共几页
     * @param pageIndex 第几页
     * @return 分页sql
     */
    @NonNull
    default String getPage(int pageSize, int pageIndex) {
        return " LIMIT " + pageSize + " OFFSET " + pageIndex;
    }

    /**
     * 获取Database
     * @return Database
     */
    @NonNull
    default Database getDatabase() {
        return DatabaseManger.getDatabase();
    }

    /**
     * 判断数据库是否存在
     * @param tableName 表名
     * @return true 数据库存在
     */
    default boolean isExist(@NonNull final String tableName) {
        return getDatabase().tableIsExist(tableName);
    }

    /**
     * 获取数据表语句
     * @param tableName 表名
     * @return 表语句
     */
    @Nullable
    default String getCreateSqlForMaster(@NonNull final String tableName) {
        return getDatabase().getCreateSqlForMaster(tableName);
    }

    /**
     * 执行sql语句
     * @param sql 需要执行的语句
     * @return true 成功
     */
    default boolean execute(@NonNull String sql) {
        return getDatabase().execSQL(sql);
    }

    /**
     * 执行sql语句
     * @param sql 需要执行的语句
     * @param arguments 条件
     * @return true 成功
     */
    default boolean execute(@NonNull final String sql, @NonNull final Object[] arguments) {
        return getDatabase().execSQL(sql, arguments);
    }

    /**
     * 更新数据
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default int rawUpdate(@NonNull final String sql) {
        return getDatabase().rawUpdate(sql, null);
    }

    /**
     * 更新数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default int rawUpdate(@NonNull final String sql, @Nullable final Object[] whereArgs) {
        return getDatabase().rawUpdate(sql, whereArgs);
    }

    /**
     * 删除数据 
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default int rawDelete(@NonNull final String sql) {
        return getDatabase().rawDelete(sql, null);
    }
    /**
     * 删除数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default int rawDelete(@NonNull final String sql, @Nullable final Object[] whereArgs) {
        return getDatabase().rawDelete(sql, whereArgs);
    }
    /**
     * 插入数据
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default long rawInsert(@NonNull final String sql) {
        return getDatabase().rawInsert(sql, null);
    }
    /**
     * 插入数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default long rawInsert(@NonNull final String sql, @Nullable final Object[] whereArgs) {
        return getDatabase().rawInsert(sql, whereArgs);
    }

    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    @Nullable
    default List<Map<String, Object>> rawQuery(@NonNull final String sql) {
        return getDatabase().rawQuery(sql, null);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param arguments 条件
     * @return 返回的结果
     */
    @Nullable
    default List<Map<String, Object>> rawQuery(@NonNull final String sql, @Nullable final Object[] arguments) {
        return getDatabase().rawQuery(sql, arguments);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    @Nullable
    default Map<String, Object> rawQueryFirst(@NonNull final String sql) {
        return getDatabase().rawQueryFirst(sql, null);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param arguments 条件
     * @return 返回的结果
     */
    @Nullable
    default Map<String, Object> rawQueryFirst(@NonNull final String sql, @Nullable final Object[] arguments) {
        return getDatabase().rawQueryFirst(sql, arguments);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    @Nullable
    default Cursor rawQueryCursor(@NonNull final String sql) {
        return getDatabase().rawQueryCursor(sql, null);
    }
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param selectionArgs 条件
     * @return 返回的结果
     */
    @Nullable
    default Cursor rawQueryCursor(@NonNull final String sql, @Nullable final Object[] selectionArgs) {
        return getDatabase().rawQueryCursor(sql, selectionArgs);
    }
    /**
     * 执行sel语句
     * @param sql 需要查询的语句
     * @return true 成功
     */
    default boolean execSQL(@NonNull final String sql) {
        return getDatabase().execSQL(sql);
    }
    /**
     * 执行sel语句
     * @param sql 需要查询的语句
     * @param bindArgs 条件
     * @return true 成功
     */
    default boolean execSQL(@NonNull final String sql, @NonNull final Object[] bindArgs) {
        return getDatabase().execSQL(sql, bindArgs);
    }
```


* ##### TableMapDao

```java
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
```


* ##### TableDao

```java
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

    default boolean saveOrUpdate(@NonNull final TABLE table, @NonNull final String where,
                                 @NonNull final String[] whereArgs) {
        final Map<String, Object> values = toMap(table);
        return saveOrUpdate(values, where, whereArgs);
    }

    default boolean saveOrUpdate(@NonNull final TABLE table) {
        final Map<String, Object> values = toMap(table);
        return saveOrUpdate(values);
    }

    @Nullable
    default TABLE toTable(@Nullable Cursor cursor) {
        @Nullable final List<TABLE> tableList = toTableList(cursor);
        if (tableList == null || tableList.isEmpty()) {
            return null;
        }
        return tableList.get(0);
    }

    @Nullable
    List<TABLE> toTableList(@Nullable Cursor cursor);

    @NonNull
    Map<String, Object> toMap(@NonNull final TABLE table);
```

----------------

在项目根目录的 build.gradle 添加仓库

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

在 module 的 build.gradle 添加依赖

```groovy
    api 'androidx.annotation:annotation:1.2.0'
    api 'androidx.core:core:1.5.0'
```

<br>


## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
