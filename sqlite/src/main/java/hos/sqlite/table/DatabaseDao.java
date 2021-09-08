package hos.sqlite.table;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @NonNull
    default String getPage(int pageSize, int pageIndex) {
        return " LIMIT " + pageSize + " OFFSET " + pageIndex;
    }

    @NonNull
    default Database getDatabase() {
        return DatabaseManger.getDatabase();
    }

    default boolean isExist(@NonNull final String tableName) {
        return getDatabase().tableIsExist(tableName);
    }

    @Nullable
    default String getCreateSqlForMaster(@NonNull final String tableName) {
        return getDatabase().getCreateSqlForMaster(tableName);
    }

    default boolean execute(@NonNull String sql) {
        return getDatabase().execSQL(sql);
    }

    default boolean execute(@NonNull final String sql, @NonNull final Object[] arguments) {
        return getDatabase().execSQL(sql, arguments);
    }

    default int rawUpdate(@NonNull final String sql) {
        return getDatabase().rawUpdate(sql, null);
    }

    default int rawUpdate(@NonNull final String sql, @Nullable final Object[] whereArgs) {
        return getDatabase().rawUpdate(sql, whereArgs);
    }

    default int rawDelete(@NonNull final String sql) {
        return getDatabase().rawDelete(sql, null);
    }

    default int rawDelete(@NonNull final String sql, @Nullable final Object[] whereArgs) {
        return getDatabase().rawDelete(sql, whereArgs);
    }

    default long rawInsert(@NonNull final String sql) {
        return getDatabase().rawInsert(sql, null);
    }

    default long rawInsert(@NonNull final String sql, @Nullable final Object[] whereArgs) {
        return getDatabase().rawInsert(sql, whereArgs);
    }

    @Nullable
    default List<Map<String, Object>> rawQuery(@NonNull final String sql) {
        return getDatabase().rawQuery(sql, null);
    }

    @Nullable
    default List<Map<String, Object>> rawQuery(@NonNull final String sql, @Nullable final Object[] arguments) {
        return getDatabase().rawQuery(sql, arguments);
    }

    @Nullable
    default Map<String, Object> rawQueryFirst(@NonNull final String sql) {
        return getDatabase().rawQueryFirst(sql, null);
    }

    @Nullable
    default Map<String, Object> rawQueryFirst(@NonNull final String sql, @Nullable final Object[] arguments) {
        return getDatabase().rawQueryFirst(sql, arguments);
    }

    @Nullable
    default Cursor rawQueryCursor(@NonNull final String sql) {
        return getDatabase().rawQueryCursor(sql, null);
    }

    @Nullable
    default Cursor rawQueryCursor(@NonNull final String sql, @Nullable final Object[] selectionArgs) {
        return getDatabase().rawQueryCursor(sql, selectionArgs);
    }

    default boolean execSQL(@NonNull final String sql) {
        return getDatabase().execSQL(sql);
    }

    default boolean execSQL(@NonNull final String sql, @NonNull final Object[] bindArgs) {
        return getDatabase().execSQL(sql, bindArgs);
    }
}
