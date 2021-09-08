
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
    default String getPage(int pageSize, int pageIndex);


    /**
     * 判断数据库是否存在
     * @param tableName 表名
     * @return true 数据库存在
     */
    default boolean isExist(@NonNull final String tableName);

    /**
     * 获取数据表语句
     * @param tableName 表名
     * @return 表语句
     */
    @Nullable
    default String getCreateSqlForMaster(@NonNull final String tableName);

    /**
     * 执行sql语句
     * @param sql 需要执行的语句
     * @return true 成功
     */
    default boolean execute(@NonNull String sql);

    /**
     * 执行sql语句
     * @param sql 需要执行的语句
     * @param arguments 条件
     * @return true 成功
     */
    default boolean execute(@NonNull final String sql, @NonNull final Object[] arguments);

    /**
     * 更新数据
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default int rawUpdate(@NonNull final String sql);

    /**
     * 更新数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default int rawUpdate(@NonNull final String sql, @Nullable final Object[] whereArgs);

    /**
     * 删除数据 
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default int rawDelete(@NonNull final String sql);
    /**
     * 删除数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default int rawDelete(@NonNull final String sql, @Nullable final Object[] whereArgs);
    /**
     * 插入数据
     * @param sql 需要执行的语句
     * @return 成功几条
     */
    default long rawInsert(@NonNull final String sql);
    /**
     * 插入数据
     * @param sql 需要执行的语句
     * @param whereArgs 条件
     * @return 成功几条
     */
    default long rawInsert(@NonNull final String sql, @Nullable final Object[] whereArgs);

    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    @Nullable
    default List<Map<String, Object>> rawQuery(@NonNull final String sql);
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param arguments 条件
     * @return 返回的结果
     */
    @Nullable
    default List<Map<String, Object>> rawQuery(@NonNull final String sql, @Nullable final Object[] arguments);
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    @Nullable
    default Map<String, Object> rawQueryFirst(@NonNull final String sql);
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param arguments 条件
     * @return 返回的结果
     */
    @Nullable
    default Map<String, Object> rawQueryFirst(@NonNull final String sql, @Nullable final Object[] arguments);
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @return 返回的结果
     */
    @Nullable
    default Cursor rawQueryCursor(@NonNull final String sql);
    /**
     * 查询数据
     * @param sql 需要查询的语句
     * @param selectionArgs 条件
     * @return 返回的结果
     */
    @Nullable
    default Cursor rawQueryCursor(@NonNull final String sql, @Nullable final Object[] selectionArgs) ;
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
    default boolean execSQL(@NonNull final String sql, @NonNull final Object[] bindArgs);
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
    default String getCreateTableSql();

    /**
     * 主键
     */
    @NonNull
    default String getCreateTablePrimaryKeySql();

    /**
     * 判断数据表是否存在
     * @return true 存在
     */
    default boolean isExist();

    /**
     * 创建表
     * @param createTableSql 创建sql
     * @param createTablePrimaryKeySql 主键sql
     */
    void createTable(@NonNull final String createTableSql, @NonNull final String createTablePrimaryKeySql);

    /**
     * 检查表是否存在，不存在则创建
     */
    default void checkTableExist();

    /**
     * 获取表的创建语句
     * @return 表的创建语句
     */
    @Nullable
    default String getCreateSqlForMaster();

    /**
     *  执行sql语句
     * @param sql 需要执行的语句
     * @return true 执行成功
     */
    default boolean execute(@NonNull String sql);
    /**
     * 执行sql语句
     * @param sql 需要执行的语句
     * @param arguments 条件
     * @return true 成功
     */
    default boolean execute(@NonNull final String sql, @NonNull final Object[] arguments);

    /**
     * 根据主键查询
     * @param primaryKeyValue 主键条件
     * @return 查询结果
     */
    @Nullable
    default Map<String, Object> queryFirstByPrimaryKey(@NonNull final Object primaryKeyValue);

    @Nullable
    default List<Map<String, Object>> query(boolean distinct, @Nullable final String[] columns,
                                            @NonNull final String where, @NonNull final Object[] whereArgs, @Nullable final String groupBy,
                                            @Nullable final String having, @Nullable final String orderBy, @Nullable final Integer limit,
                                            @Nullable final Integer offset);

    @Nullable
    default Map<String, Object> queryFirst(boolean distinct, @Nullable final String[] columns, String where,
                                           Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                           @Nullable final String orderBy, @Nullable final Integer limit, @Nullable final Integer offset);

    /**
     * 查询全部
     * @return 查询结果
     */
    @Nullable
    default List<Map<String, Object>> queryAll();

    @Nullable
    default List<Map<String, Object>> query(@Nullable final String[] columns, @NonNull final String where,
                                            @NonNull final Object[] whereArgs);

    @Nullable
    default List<Map<String, Object>> query(@Nullable final String[] columns, @NonNull final String where,
                                            @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                            @Nullable final String orderBy);

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String where, @NonNull final Object[] whereArgs);

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String where, @NonNull final Object[] whereArgs,
                                            @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy);

    @Nullable
    default List<Map<String, Object>> query(@NonNull final String where, @NonNull final Object[] whereArgs,
                                            @Nullable final Integer limit, @Nullable final Integer offset);

    @Nullable
    default List<Map<String, Object>> query(@Nullable final String[] columns, @NonNull final String where,
                                            @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset);

    @Nullable
    default Map<String, Object> queryFirst(@Nullable final String[] columns, @NonNull final String where,
                                           @NonNull final Object[] whereArgs);

    @Nullable
    default Map<String, Object> queryFirst(@Nullable final String[] columns, @NonNull final String where,
                                           @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                           @Nullable final String orderBy);

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String where, @NonNull final Object[] whereArgs)‘

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String where, @NonNull final Object[] whereArgs,
                                           @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy);

    @Nullable
    default Map<String, Object> queryFirst(@NonNull final String where, @NonNull final Object[] whereArgs,
                                           @Nullable final Integer limit, @Nullable final Integer offset);
    @Nullable
    default Map<String, Object> queryFirst(@Nullable final String[] columns, @NonNull final String where,
                                           @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset);

    @Nullable
    default Cursor queryCursorAll();

    @Nullable
    default Cursor queryCursor(@NonNull final String where, @NonNull final Object[] whereArgs);

    default Cursor queryCursor(@Nullable final String[] columns, @NonNull final String where,
                               @NonNull final Object[] whereArgs);

    default Cursor queryCursor(@NonNull final String where, @NonNull final Object[] whereArgs,
                               @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy);

    default Cursor queryCursor(@Nullable final String[] columns, @NonNull final String where,
                               @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                               @Nullable final String orderBy) {
        checkTableExist();
        return getDatabase().queryCursor(getTableName(), columns, where, whereArgs, groupBy, having, orderBy);
    }

    default Cursor queryCursor(@NonNull final String where, @NonNull final Object[] whereArgs,
                               @Nullable final Integer limit, @Nullable final Integer offset);

    default Cursor queryCursor(@Nullable final String[] columns, @NonNull final String where,
                               @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset);

    default Cursor queryCursor(boolean distinct, @Nullable final String[] columns, @NonNull final String where,
                               @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                               @Nullable final String orderBy, @Nullable final Integer limit, @Nullable final Integer offset);

    default long insert(@NonNull final List<Map<String, Object>> values,
                        @NonNull final ConflictAlgorithm conflictAlgorithm);

    default long insert(@NonNull final Map<String, Object> values, @NonNull final ConflictAlgorithm conflictAlgorithm);

    default long insert(@NonNull final List<Map<String, Object>> values);


    default long insert(@NonNull final Map<String, Object> values);

    default long update(@NonNull final Map<String, Object> values, @NonNull final String where,
                        @NonNull final Object[] whereArgs, @NonNull final ConflictAlgorithm conflictAlgorithm);

    default long update(@NonNull final Map<String, Object> values, @NonNull final String where,
                        @NonNull final Object[] whereArgs);

    default long delete(@NonNull final String where, @NonNull final Object[] whereArgs);

    /**
     * 清空表的数据
     * @return true 清空的条数
     */
    default long clearTableData();

    /**
     * 根据主键删除数据
     * @param whereIn 删除条件
     * @return true 删除成功
     */
    default boolean deleteByPrimaryKey(@NonNull final Object[] whereIn);
    /**
     * 根据主键删除数据
     * @param primaryKeyValue 删除条件
     * @return 删除条数
     */
    default long deleteByPrimaryKey(@NonNull final Object primaryKeyValue);

    /**
     * 保存或者更新数据
     * @param values 更新的数据
     * @param where 更新的条件sql
     * @param whereArgs 更新的条件
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final Map<String, Object> values, @NonNull final String where,
                                 @NonNull final String[] whereArgs);
    /**
     * 根据主键保存或者更新数据
     * @param map 更新的数据
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final Map<String, Object> map);

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
    default void beforeUpdate(@NonNull final Map<String, Object> mapValues, @NonNull final Map<String, Object> oldMap);
```


* ##### TableDao

```java
    @Nullable
    default TABLE queryFirstTableByPrimaryKey(@NonNull final Object primaryKeyValue);

    @Nullable
    default List<TABLE> queryTable(boolean distinct, @Nullable final String[] columns, @NonNull final String where,
                                   @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                   @Nullable final String orderBy, @Nullable final Integer limit, @Nullable final Integer offset);

    @Nullable
    default TABLE queryFirstTable(boolean distinct, @Nullable final String[] columns, @NonNull final String where,
                                  @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                  @Nullable final String orderBy, @Nullable final Integer limit, @Nullable final Integer offset);

    @Nullable
    default List<TABLE> queryTable(@Nullable final String[] columns, @NonNull final String where,
                                   @NonNull final Object[] whereArgs);

    @Nullable
    default List<TABLE> queryTable(@Nullable final String[] columns, @NonNull final String where,
                                   @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                   @Nullable final String orderBy);

    @Nullable
    default List<TABLE> queryTableAll();

    @Nullable
    default List<TABLE> queryTable(@NonNull final String where, @NonNull final Object[] whereArgs);

    @Nullable
    default List<TABLE> queryTable(@NonNull final String where, @NonNull final Object[] whereArgs,
                                   @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy);

    @Nullable
    default List<TABLE> queryTable(@NonNull final String where, @NonNull final Object[] whereArgs,
                                   @Nullable final Integer limit, @Nullable final Integer offset);

    @Nullable
    default List<TABLE> queryTable(@Nullable final String[] columns, @NonNull final String where,
                                   @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset);

    @Nullable
    default TABLE queryFirstTable(@Nullable final String[] columns, @NonNull final String where,
                                  @NonNull final Object[] whereArgs);

    @Nullable
    default TABLE queryFirstTable(@Nullable final String[] columns, @NonNull final String where,
                                  @NonNull final Object[] whereArgs, @Nullable final String groupBy, @Nullable final String having,
                                  @Nullable final String orderBy);

    @Nullable
    default TABLE queryFirstTable(@NonNull final String where, @NonNull final Object[] whereArgs);

    @Nullable
    default TABLE queryFirstTable(@NonNull final String where, @NonNull final Object[] whereArgs,
                                  @Nullable final String groupBy, @Nullable final String having, @Nullable final String orderBy);

    @Nullable
    default TABLE queryFirstTable(@NonNull final String where, @NonNull final Object[] whereArgs,
                                  @Nullable final Integer limit, @Nullable final Integer offset);

    @Nullable
    default TABLE queryFirstTable(@Nullable final String[] columns, @NonNull final String where,
                                  @NonNull final Object[] whereArgs, @Nullable final Integer limit, @Nullable final Integer offset);
    /**
     * 保存或者更新数据
     * @param table 更新的实体
     * @param where 更新的条件sql
     * @param whereArgs 更新的条件
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final TABLE table, @NonNull final String where,
                                 @NonNull final String[] whereArgs);
    /**
     * 根据主键保存或者更新数据
     * @param table 更新的实体
     * @return true 保存或者更新成功
     */
    default boolean saveOrUpdate(@NonNull final TABLE table);

    /**
     * 将cursor转换为实体
     */
    @Nullable
    default TABLE toTable(@Nullable Cursor cursor);

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
