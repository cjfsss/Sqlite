package hos.sqlite;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import hos.sqlite.datebase.Database;
import hos.sqlite.exception.SQLArgumentException;
import hos.sqlite.exception.SQLNullException;
import hos.sqlite.statement.SQLiteDatabase;

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

    private DatabaseConfig(Builder builder) {
        if (builder.mSqLiteDatabase == null) {
            throw new SQLArgumentException("SqLiteDatabase or DatabasePath is not null");
        }
        this.mSqLiteDatabase = builder.mSqLiteDatabase;
        this.mIsDatabaseExitMap = new HashMap<>();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    SQLiteDatabase getSqLiteDatabase() {
        return mSqLiteDatabase;
    }

    Database getDatabase() {
        if (mDatabase == null) {
            return new DatabaseImpl();
        }
        return mDatabase;
    }

    Map<String, Boolean> getIsDatabaseExitMap() {
        return mIsDatabaseExitMap;
    }

    public final static class Builder {
        private SQLiteDatabase mSqLiteDatabase;

        private Builder() {
        }

        public Builder database(final android.database.sqlite.SQLiteDatabase database) throws SQLNullException {
            return database(DatabaseManger.open(database));
        }

        public Builder database(final File file,
                                final android.database.sqlite.SQLiteDatabase.CursorFactory factory) throws SQLNullException {
            return database(DatabaseManger.open(file, factory));
        }

        public Builder database(SQLiteDatabase mSqLiteDatabase) {
            this.mSqLiteDatabase = mSqLiteDatabase;
            return this;
        }

        public Builder database(Context context, String path) {
            return database(DatabaseManger.open(context, path));
        }

        public Builder database(Context context, File file) {
            return database(DatabaseManger.open(context, file));
        }

        public DatabaseConfig build() {
            return new DatabaseConfig(this);
        }
    }
}
