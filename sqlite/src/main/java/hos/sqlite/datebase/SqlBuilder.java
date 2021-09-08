package hos.sqlite.datebase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hos.sqlite.exception.SQLArgumentException;
import hos.sqlite.statement.SQLiteStatement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title: SqlBuilder </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/15 10:50
 */
public class SqlBuilder {

    public String sql;
    public String sqlCreateTable;
    public String sqlCreatePrimaryKey;
    public String[] arguments;
    public String[] whereArgs;
    public final ContentValues contentValues = new ContentValues();
    public final List<ContentValues> contentValuesList = new ArrayList<>();
    public int conflictIndex;

    private static final String[] CONFLICT_VALUES = new String[]{"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};

    public Map<String, Object> mapValues;
    public List<Map<String, Object>> listMapValues;

    public SqlBuilder() {
    }

    private void _writeClause(StringBuffer s, String name, Object clause) {
        if (clause != null) {
            s.append(name);
            s.append(clause);
        }
    }

    private void _writeColumns(StringBuffer s, String[] columns) {
        final int length = columns.length;
        for (int i = 0; i < length; i++) {
            final String column = columns[i];
            if (column != null) {
                if (i > 0) {
                    s.append(", ");
                }
                s.append(column);
            }
        }
        s.append(" ");
    }

    private String[] _writeWhere(Object[] whereArgs) {
        if (whereArgs != null && whereArgs.length != 0) {
            int length = whereArgs.length;
            String[] arguments = new String[length];
            for (int i = 0; i < length; i++) {
                arguments[i] = String.valueOf(whereArgs[i]);
            }
            return arguments;
        }
        return null;
    }

    public SqlBuilder arguments(Object[] arguments) {
        this.arguments = _writeWhere(arguments);
        return this;
    }

    public SqlBuilder whereArgs(Object[] whereArgs) {
        this.whereArgs = _writeWhere(whereArgs);
        return this;
    }

    public SqlBuilder createTable(String tableName) {
        sqlCreateTable = "CREATE TABLE IF NOT EXISTS '" + tableName + "' ";
        return this;
    }

    public SqlBuilder createTablePrimaryKey(Object primaryKey) {
        sqlCreatePrimaryKey = " '" + primaryKey + "' INTEGER PRIMARY KEY AUTOINCREMENT ";
        return this;
    }

    public SqlBuilder sql(String sql, Object[] arguments) {
        this.sql = sql;
        return whereArgs(arguments);
    }

    public SqlBuilder sql(String table, String where, Object[] arguments) {
        String sql = table;
        if (!table.toUpperCase().contains("SELECT")) {
            sql = " SELECT * FROM " + table;
        }
        if (!where.toUpperCase().contains("WHERE")) {
            sql += " WHERE " + where;
        }
        this.sql = sql;
        return whereArgs(arguments);
    }

    public SqlBuilder query(String table, boolean distinct, String[] columns, String where, Object[] whereArgs,
                            String groupBy, String having, String orderBy, Integer limit, Integer offset) {
        if (groupBy == null && having != null) {
            throw new SQLArgumentException("HAVING clauses are only permitted when using a groupBy clause ");
        }
        final StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        if (distinct) {
            query.append("DISTINCT ");
        }
        if (columns != null && columns.length != 0) {
            _writeColumns(query, columns);
        } else {
            query.append(" * ");
        }
        query.append("FROM ");
        query.append(table);
        if (!TextUtils.isEmpty(where) && !where.contains("WHERE") && !where.contains("where")) {
            _writeClause(query, " WHERE ", where);
        }
        if (!TextUtils.isEmpty(groupBy) && !groupBy.contains("GROUP BY") && !groupBy.contains("group by")) {
            _writeClause(query, " GROUP BY ", groupBy);
        }
        if (!TextUtils.isEmpty(having) && !having.contains("HAVING") && !having.contains("having")) {
            _writeClause(query, " HAVING ", having);
        }
        if (!TextUtils.isEmpty(orderBy) && !orderBy.contains("ORDER BY") && !orderBy.contains("ORDER BY")) {
            _writeClause(query, " ORDER BY ", orderBy);
        }
        if (limit != null) {
            _writeClause(query, " LIMIT ", limit);
        }
        if (offset != null) {
            _writeClause(query, " OFFSET ", offset);
        }
        sql = query.toString();
        whereArgs(whereArgs);
        return this;
    }

    public SqlBuilder insert(String table, ContentValues values, String nullColumnHack,
                             ConflictAlgorithm conflictAlgorithm) {
        return insert(table, contentValuesToMap(values).mapValues, nullColumnHack,
                conflictAlgorithm(conflictAlgorithm).conflictIndex);
    }

    public SqlBuilder insert(String table, Map<String, Object> values, String nullColumnHack,
                             ConflictAlgorithm conflictAlgorithm) {
        return insert(table, values, nullColumnHack, conflictAlgorithm(conflictAlgorithm).conflictIndex);
    }

    public SqlBuilder insert(String table, Map<String, Object> values, String nullColumnHack, Integer conflictIndex) {
        final StringBuilder insert = new StringBuilder();
        insert.append("INSERT");
        if (conflictIndex != null) {
            insert.append(CONFLICT_VALUES[conflictIndex]);
        }
        insert.append(" INTO ");
        insert.append(table);
        insert.append(" (");

        final List<String> bindArgs = new ArrayList<>();
        final int size = (values != null) ? values.size() : 0;

        if (size > 0) {
            this.contentValues.clear();
            final StringBuilder sbValues = new StringBuilder(") VALUES (");
            int i = 0;
            for (String colName : values.keySet()) {
                Object value = values.get(colName);
                getContentValue(this.contentValues, colName, value);
                if (i++ > 0) {
                    insert.append(", ");
                    sbValues.append(", ");
                }
                insert.append(colName);
                if (value == null) {
                    sbValues.append("NULL");
                } else {
                    bindArgs.add(String.valueOf(value));
                    sbValues.append("?");
                }
            }
            insert.append(sbValues);
        } else {
            if (nullColumnHack == null) {
                throw new SQLArgumentException("nullColumnHack required when inserting no data");
            }
            insert.append(nullColumnHack).append(") VALUES (NULL");
        }
        insert.append(") ");

        sql = insert.toString();
        arguments = bindArgs.toArray(new String[]{});
        return this;
    }

    public SqlBuilder update(String table, ContentValues values, String where, Object[] whereArgs,
                             ConflictAlgorithm conflictAlgorithm) {
        return update(table, contentValuesToMap(values).mapValues, where, whereArgs, conflictAlgorithm);
    }

    public SqlBuilder update(String table, Map<String, Object> values, String where, Object[] whereArgs,
                             ConflictAlgorithm conflictAlgorithm) {
        if (values == null || values.size() == 0) {
            throw new SQLArgumentException("Empty values");
        }
        final StringBuffer update = new StringBuffer();
        update.append("UPDATE ");
        if (conflictAlgorithm != null) {
            update.append(CONFLICT_VALUES[conflictAlgorithm(conflictAlgorithm).conflictIndex]);
        }
        update.append(table);
        update.append(" SET ");

        final List<String> bindArgs = new ArrayList<>();
        int i = 0;
        this.contentValues.clear();
        for (String colName : values.keySet()) {
            update.append((i++ > 0) ? ", " : " ");
            update.append(colName);
            Object value = values.get(colName);
            getContentValue(this.contentValues, colName, value);
            if (value != null) {
                bindArgs.add(String.valueOf(value));
                update.append(" = ?");
            } else {
                update.append(" = NULL");
            }
        }
        if (whereArgs != null && whereArgs.length != 0) {
            int length = whereArgs.length;
            this.whereArgs = new String[length];
            for (int j = 0; j < length; j++) {
                this.whereArgs[j] = String.valueOf(whereArgs[j]);
                bindArgs.add(String.valueOf(whereArgs[j]));
            }
        }
        _writeClause(update, " WHERE ", where);
        sql = update.toString();
        arguments = bindArgs.toArray(new String[]{});
        return this;
    }

    public SqlBuilder delete(String table, String where, Object[] whereArgs) {
        final StringBuffer delete = new StringBuffer();
        delete.append("DELETE FROM ");
        delete.append(table);
        if (!TextUtils.isEmpty(where) && !where.contains("WHERE") && !where.contains("where")) {
            _writeClause(delete, " WHERE ", where);
        }
        sql = delete.toString();
        return whereArgs(whereArgs);
    }

    public SqlBuilder whereIn(String table, String primaryKey, Object[] whereIn) {
        final StringBuffer delete = new StringBuffer();
        delete.append("DELETE FROM ");
        delete.append(table);
        _writeClause(delete, " WHERE ", primaryKey);
        delete.append(toWhereIn(whereIn));
        sql = delete.toString();
        return this;
    }


    public static String toWhereIn(@NonNull final Object... userIdArray) {
        int length = userIdArray.length;
        StringBuilder builder = new StringBuilder();
        builder.append(" IN (");
        if (length == 1) {
            builder.append(" '").append(userIdArray[0]).append("' ").append(") ");
            return builder.toString();
        }
        for (int i = 0; i < length; i++) {
            if (i + 1 == length) {
                builder.append(" '").append(userIdArray[i]).append("' ").append(") ");
            } else {
                builder.append(" '").append(userIdArray[i]).append("', ");
            }
        }
        return builder.toString();
    }

    public SqlBuilder conflictAlgorithm(ConflictAlgorithm conflictAlgorithm) {
        if (conflictAlgorithm.equals(ConflictAlgorithm.fail)) {
            conflictIndex = SQLiteDatabase.CONFLICT_FAIL;
        } else if (conflictAlgorithm.equals(ConflictAlgorithm.abort)) {
            conflictIndex = SQLiteDatabase.CONFLICT_ABORT;
        } else if (conflictAlgorithm.equals(ConflictAlgorithm.ignore)) {
            conflictIndex = SQLiteDatabase.CONFLICT_IGNORE;
        } else if (conflictAlgorithm.equals(ConflictAlgorithm.replace)) {
            conflictIndex = SQLiteDatabase.CONFLICT_REPLACE;
        } else if (conflictAlgorithm.equals(ConflictAlgorithm.rollback)) {
            conflictIndex = SQLiteDatabase.CONFLICT_ROLLBACK;
        } else {
            conflictIndex = SQLiteDatabase.CONFLICT_NONE;
        }
        return this;
    }

    public SqlBuilder contentValues(List<Map<String, Object>> valueList) {
        contentValuesList.clear();
        for (Map<String, Object> map : valueList) {
            final ContentValues contentValues = new ContentValues();
            for (String key : map.keySet()) {
                getContentValue(contentValues, key, map.get(key));
            }
            contentValuesList.add(contentValues);
        }
        return this;
    }

    public SqlBuilder contentValues(@NonNull final Map<String, Object> map) {
        /* 重新组装一下数据 */
        for (String key : map.keySet()) {
            getContentValue(contentValues, key, map.get(key));
        }
        return this;
    }

    public SqlBuilder contentValuesToMap(@NonNull final ContentValues values) {
        /* 重新组装一下数据 */
        mapValues = new HashMap<>();
        for (String key : values.keySet()) {
            getMap(mapValues, key, values.get(key));
        }
        return this;
    }

    @Nullable
    public Object getValue(@NonNull final Cursor cursor, final int index) {
        try {
            return cursor.getString(index);
        } catch (Exception e) {
            try {
                return cursor.getInt(index);
            } catch (Exception e1) {
                try {
                    return cursor.getDouble(index);
                } catch (Exception e2) {
                    try {
                        return cursor.getLong(index);
                    } catch (Exception e3) {
                        try {
                            return cursor.getFloat(index);
                        } catch (Exception e4) {
                            try {
                                return cursor.getShort(index);
                            } catch (Exception e5) {
                                return cursor.getBlob(index);
                            }
                        }
                    }
                }
            }
        }
    }

    private void getContentValue(@NonNull final ContentValues contentValues, @NonNull final String key,
                                 @Nullable final Object value) {
        if (value instanceof Integer) {
            contentValues.put(key, Integer.parseInt(String.valueOf(value)));
        } else if (value instanceof Long) {
            contentValues.put(key, Long.parseLong(String.valueOf(value)));
        } else if (value instanceof Double) {
            contentValues.put(key, Double.parseDouble(String.valueOf(value)));
        } else if (value instanceof Float) {
            contentValues.put(key, Float.parseFloat(String.valueOf(value)));
        } else if (value instanceof Short) {
            contentValues.put(key, Short.parseShort(String.valueOf(value)));
        } else if (value instanceof Boolean) {
            contentValues.put(key, Boolean.parseBoolean(String.valueOf(value)));
        } else if (value instanceof Byte) {
            contentValues.put(key, Byte.valueOf(String.valueOf(value)));
        } else {
            if (isEmpty(value)) {
                contentValues.put(key, "");
            } else {
                contentValues.put(key, String.valueOf(value));
            }
        }
    }

    private static void getMap(@NonNull final Map<String, Object> map, @NonNull final String key,
                               @Nullable final Object value) {
        if (value instanceof Integer) {
            map.put(key, Integer.parseInt(String.valueOf(value)));
        } else if (value instanceof Long) {
            map.put(key, Long.parseLong(String.valueOf(value)));
        } else if (value instanceof Double) {
            map.put(key, Double.parseDouble(String.valueOf(value)));
        } else if (value instanceof Float) {
            map.put(key, Float.parseFloat(String.valueOf(value)));
        } else if (value instanceof Short) {
            map.put(key, Short.parseShort(String.valueOf(value)));
        } else if (value instanceof Boolean) {
            map.put(key, Boolean.parseBoolean(String.valueOf(value)));
        } else if (value instanceof Byte) {
            map.put(key, Byte.valueOf(String.valueOf(value)));
        } else {
            if (isEmpty(value)) {
                map.put(key, "");
            } else {
                map.put(key,  String.valueOf(value));
            }
        }
    }

    public <STATEMENT extends SQLiteStatement<STATEMENT>> void statement(@NonNull final SQLiteStatement<STATEMENT> statement,
                                                                         @Nullable final Object[] objects) {
        if (objects == null || objects.length == 0) {
            return;
        }
        int index = 0;
        for (Object value : objects) {
            index++;
            statement(statement, index, value);
        }
    }

    public <STATEMENT extends SQLiteStatement<STATEMENT>> void statement(@NonNull final SQLiteStatement<STATEMENT> sqLiteStatement, int index, @Nullable final Object value) {
        if (value == null) {
            sqLiteStatement.bindNull(index);
            return;
        }
        if (value instanceof Integer) {
            sqLiteStatement.bindLong(index, Integer.parseInt(String.valueOf(value)));
        } else if (value instanceof Long) {
            sqLiteStatement.bindLong(index, Long.parseLong(String.valueOf(value)));
        } else if (value instanceof Double) {
            sqLiteStatement.bindDouble(index, Double.parseDouble(String.valueOf(value)));
        } else {
            if (isEmpty(value)) {
                sqLiteStatement.bindNull(index);
            } else {
                sqLiteStatement.bindString(index, String.valueOf(value));
            }
        }
    }

    public <STATEMENT extends SQLiteStatement<STATEMENT>> void statement(@NonNull final SQLiteStatement<STATEMENT> statement, @Nullable final Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        int index = 0;
        for (String key : map.keySet()) {
            index++;
            final Object value = map.get(key);
            statement(statement, index, value);
        }
    }


    public SqlBuilder mergeMap(Map<String, Object> oldMap, Map<String, Object> values, String createSqlForMaster) {
        @NonNull final Set<String> keySet = oldMap.keySet();
        for (String key : keySet) {
            // 这里判断字段是否已经存在该表中
            if (createSqlForMaster != null && !createSqlForMaster.contains(key)) {
                // 不包含,直接跳过
                continue;
            }
            @Nullable final Object value = values.get(key);
            if (value != null) {
                getMap(oldMap, key, value);
            }
        }
        this.mapValues = oldMap;
        return this;
    }

    public SqlBuilder filterMap(Map<String, Object> values, String createSqlForMaster) {
        Map<String, Object> oldMap = new HashMap<>();
        @NonNull final Set<String> keySet = values.keySet();
        for (String key : keySet) {
            // 这里判断字段是否已经存在该表中
            if (createSqlForMaster != null && !createSqlForMaster.contains(key)) {
                // 不包含,直接跳过
                continue;
            }
            @Nullable final Object value = values.get(key);
            if (value != null) {
                getMap(oldMap, key, value);
            }
        }
        this.mapValues = oldMap;
        return this;
    }

    public SqlBuilder json(String json, String sqlCreate) throws JSONException {
        @NonNull final List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        @NonNull final JSONArray jsonArray = new JSONArray(json);
        @NonNull final int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            @NonNull final JSONObject jsonObject = jsonArray.getJSONObject(i);
            @NonNull final Iterator<String> keys = jsonObject.keys();
            Map<String, Object> map = new HashMap<>();
            for (Iterator<String> keyI = keys; keyI.hasNext(); ) {
                @NonNull final String key = (String) keyI.next();
                // 这里判断字段是否已经存在该表中
                if (sqlCreate != null && !sqlCreate.contains(key)) {
                    // 不包含,直接跳过
                    continue;
                }
                @Nullable final Object value = jsonObject.get(key);
                getMap(map, key, value);
            }
            if (map.size() > 0) {
                listMap.add(map);
            }
        }
        listMapValues = listMap;
        return this;
    }

    /**
     * 是否是空的
     *
     * @param value 目标值
     * @return true 空
     */
    public static boolean isEmpty(Object value) {
        return value == null || TextUtils.isEmpty(String.valueOf(value))
                || TextUtils.equals(String.valueOf(value), "null")
                || TextUtils.equals(String.valueOf(value), "NULL");
    }

    @Override
    public String toString() {
        return "SqlBuilder{" + "sql='" + sql + '\'' + ", sqlCreateTable='" + sqlCreateTable + '\'' +
                ", sqlCreatePrimaryKey='" + sqlCreatePrimaryKey + '\'' + ", arguments=" + Arrays.toString(arguments) +
                ", whereArgs=" + Arrays.toString(whereArgs) + ", contentValues=" + contentValues +
                ", contentValuesList=" + contentValuesList + ", conflictIndex=" + conflictIndex + ", mapValues=" +
                mapValues + ", listMapValues=" + listMapValues + '}';
    }
}
