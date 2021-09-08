package hos.sqlite;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import hos.sqlite.exception.SQLArgumentException;
import hos.sqlite.exception.SQLNullException;
import hos.sqlite.statement.SQLiteDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: DatabaseConfig </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/1/27 11:34
 */
public class DatabaseConfig {

    private final SQLiteDatabase mSqLiteDatabase;

    private final Map<String, Boolean> mIsDatabaseExitMap;

    private DatabaseImpl mDatabase;

    public DatabaseConfig(Builder builder) {
        if (builder.mSqLiteDatabase == null && TextUtils.isEmpty(builder.mDatabasePath)) {
            throw new SQLArgumentException("SqLiteDatabase or DatabasePath is not null");
        }
        if (builder.mSqLiteDatabase == null) {
            boolean orExistsDir = isFileExists(builder.mContext, builder.mDatabasePath);
            if (!orExistsDir) {
                // 文件创建失败
                throw new SQLArgumentException(" DatabasePath create error ");
            } else {
                // 文件创建成功
                this.mSqLiteDatabase = new SQLAndroidDatabase()
                        .openOrCreateDatabase(getFileByPath(builder.mDatabasePath), null);
            }
        } else {
            this.mSqLiteDatabase = builder.mSqLiteDatabase;
        }
        this.mIsDatabaseExitMap = new HashMap<>();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    SQLiteDatabase getSqLiteDatabase() {
        return mSqLiteDatabase;
    }

    DatabaseImpl getDatabase() {
        if (mDatabase == null) {
            return new DatabaseImpl();
        }
        return mDatabase;
    }

    Map<String, Boolean> getIsDatabaseExitMap() {
        return mIsDatabaseExitMap;
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public File getFileByPath(final String filePath) {
        return new File(filePath);
    }

    /**
     * Return whether the file exists.
     *
     * @param file The file.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isFileExists(Context context, final File file) {
        if (file == null) return false;
        if (file.exists()) {
            return true;
        }
        return isFileExists(context, file.getAbsolutePath());
    }

    /**
     * Return whether the file exists.
     *
     * @param filePath The path of file.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isFileExists(Context context, final String filePath) {
        File file = getFileByPath(filePath);
        if (file == null) return false;
        if (file.exists()) {
            return true;
        }
        return isFileExistsApi29(context, filePath);
    }

    private boolean isFileExistsApi29(Context context, String filePath) {
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                Uri uri = Uri.parse(filePath);
                ContentResolver cr = context.getContentResolver();
                AssetFileDescriptor afd = cr.openAssetFileDescriptor(uri, "r");
                if (afd == null) return false;
                try {
                    afd.close();
                } catch (IOException ignore) {
                }
            } catch (FileNotFoundException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final static class Builder {
        private SQLiteDatabase mSqLiteDatabase;

        private String mDatabasePath;

        private Context mContext;

        private Builder() {
        }

        public Builder setSqLiteDatabase(@NonNull final android.database.sqlite.SQLiteDatabase database) throws SQLNullException {
            return setSqLiteDatabase(new SQLAndroidDatabase().openOrCreateDatabase(database));
        }

        public Builder setSqLiteDatabase(SQLiteDatabase mSqLiteDatabase) {
            this.mSqLiteDatabase = mSqLiteDatabase;
            return this;
        }

        public Builder databasePath(Context context, String databasePath) {
            mContext = context.getApplicationContext();
            this.mDatabasePath = databasePath;
            return this;
        }

        public DatabaseConfig build() {
            return new DatabaseConfig(this);
        }
    }
}
