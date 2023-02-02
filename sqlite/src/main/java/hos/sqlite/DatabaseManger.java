package hos.sqlite;

import android.content.Context;
import android.util.Log;


import java.io.File;

import hos.sqlite.datebase.Database;
import hos.sqlite.exception.SQLArgumentException;
import hos.sqlite.exception.SQLNullException;
import hos.sqlite.statement.SQLiteDatabase;
import hos.utils.SqliteLog;

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
                else {
                    SqliteLog.w(new IllegalStateException("Only allowed to configure once."));
                }
            }
        }
    }

    public static SQLiteDatabase getSqLiteDatabase() {
        return sConfig.getSqLiteDatabase();
    }

    public static Database getDatabase() {
        return sConfig.getDatabase();
    }

    public static SQLiteDatabase open(final Context context,
                                      final File file) throws SQLNullException {
        if (file == null) {
            // 文件不存在
            throw new SQLArgumentException(" DatabasePath create error ");
        }
        return DatabaseManger.open(context, file.getAbsolutePath());
    }

    public static SQLiteDatabase open(final Context context,
                                      final String path) throws SQLNullException {
        boolean orExistsDir = FileUtils.isFileExists(context, path);
        if (!orExistsDir) {
            // 文件不存在
            throw new SQLArgumentException(" DatabasePath create error ");
        }
        // 文件创建成功
        return SQLAndroidDatabase.open(FileUtils.getFileByPath(path), null);
    }

    public static SQLiteDatabase open(final File file,
                                      final android.database.sqlite.SQLiteDatabase.CursorFactory factory) throws SQLNullException {
        return SQLAndroidDatabase.open(file, factory);
    }

    public static SQLiteDatabase open(final android.database.sqlite.SQLiteDatabase database) throws SQLNullException {
        return SQLAndroidDatabase.open(database);
    }
}
