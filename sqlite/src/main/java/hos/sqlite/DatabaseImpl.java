package hos.sqlite;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hos.sqlite.datebase.Database;
import hos.sqlite.statement.SQLiteDatabase;
import hos.utils.CloseUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: DatabaseImpl </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/1/27 18:47
 */
final class DatabaseImpl implements Database {

    @NonNull
    @Override
    public SQLiteDatabase getConnection() {
        return DatabaseManger.getSqLiteDatabase();
    }

    @Override
    public boolean tableIsExist(@NonNull String tableName) {
        @Nullable final Boolean checkedDatabase = this.isCheckedDatabase(tableName);
        if (checkedDatabase != null && checkedDatabase) {
            return true;
        }
        Cursor cursor = rawQueryCursor("SELECT COUNT(*) AS c FROM sqlite_master WHERE type=? AND name=? ",
                new String[]{"table", tableName});
        if (cursor == null) {
            return false;
        }
        try {
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    this.setCheckedDatabase(tableName, true);
                    return true;
                }
            }
        } finally {
            CloseUtils.closeQuietly(cursor);
        }
        return false;
    }

    @Nullable
    private Boolean isCheckedDatabase(@NonNull final String tableName) {
        Map<String, Boolean> isDatabaseExitMap = DatabaseManger.getConfig().getIsDatabaseExitMap();
        if (isDatabaseExitMap == null) {
            return false;
        }
        return isDatabaseExitMap.get(tableName);
    }

    private void setCheckedDatabase(@NonNull final String tableName, final boolean isExist) {
        Map<String, Boolean> isDatabaseExitMap = DatabaseManger.getConfig().getIsDatabaseExitMap();
        if (isDatabaseExitMap == null) {
            isDatabaseExitMap = new HashMap<>();
        }
        isDatabaseExitMap.put(tableName, isExist);
    }
}
