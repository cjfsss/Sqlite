package hos.sqlite;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hos.sqlite.exception.SQLNullException;
import hos.sqlite.statement.SQLiteDatabase;

import java.io.File;

/**
 * <p>Title: DatabaseManger </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/1/27 11:42
 */
public class DatabaseManger {

    private static DatabaseConfig sConfig;

    private DatabaseManger() {
    }

    public static DatabaseConfig getConfig() {
        setConfig(null);
        return sConfig;
    }

    public static void setConfig(DatabaseConfig config) {
        if (sConfig == null) {
            synchronized (DatabaseConfig.class) {
                if (sConfig == null)
                    sConfig = config == null ? DatabaseConfig.newBuilder().build() : config;
                else Log.w("Kalle", new IllegalStateException("Only allowed to configure once."));
            }
        }
    }

    /**
     * 获取sql查询的管理类
     *
     * @return
     */
    public static SQLiteDatabase getSqLiteDatabase() {
        return sConfig.getSqLiteDatabase();
    }

    public static DatabaseImpl getDatabase() {
        return sConfig.getDatabase();
    }

    public static SQLiteDatabase newConnection(@NonNull final File file,
                                               @Nullable final android.database.sqlite.SQLiteDatabase.CursorFactory factory) throws SQLNullException {
        return new SQLAndroidDatabase().openOrCreateDatabase(file, factory);
    }

    public static SQLiteDatabase connection(@NonNull final android.database.sqlite.SQLiteDatabase database) throws SQLNullException {
        return new SQLAndroidDatabase().openOrCreateDatabase(database);
    }
}
