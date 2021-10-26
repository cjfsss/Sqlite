package hos.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import hos.sqlite.datebase.ConflictAlgorithm;
import hos.sqlite.datebase.SqlBuilder;
import hos.sqlite.exception.SQLNullException;
import hos.sqlite.statement.SQLiteDatabase;

import java.io.File;

/**
 * <p>Title: SQLCipherDatabase </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/19 11:07
 */
final class SQLAndroidDatabase extends SQLiteDatabase {

    private android.database.sqlite.SQLiteDatabase mSqLiteDatabase;

    @NonNull
    SQLiteDatabase openOrCreateDatabase(@NonNull final File file,
                                        @Nullable final android.database.sqlite.SQLiteDatabase.CursorFactory factory) throws SQLNullException {
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    throw new SQLNullException("Database File create Failed ");
                }
            } catch (Exception e) {
                throw new SQLNullException("Database File create Failed ", e);
            }
        }
        try {
            mSqLiteDatabase = android.database.sqlite.SQLiteDatabase.openOrCreateDatabase(file, factory);
        } catch (Exception e) {
            Log.e("SQLiteDatabase", "数据库打开异常：" + "\n" + file.getAbsolutePath() + "\t\t找不到文件\n" + e.getMessage());
            throw new SQLNullException("Database File create Failed " + file.getAbsolutePath(), e);
        }
        Log.i("SQLiteDatabase", "getConnection successful .");
        return this;
    }

    @NonNull
    SQLiteDatabase openOrCreateDatabase(android.database.sqlite.SQLiteDatabase database) {
        mSqLiteDatabase = database;
        return this;
    }

    @Override
    public boolean enableWriteAheadLogging() {
        return mSqLiteDatabase.enableWriteAheadLogging();
    }

    @Override
    public void disableWriteAheadLogging() {
        mSqLiteDatabase.disableWriteAheadLogging();
    }

    @Override
    protected void beginTransaction() {
        if (mSqLiteDatabase.isWriteAheadLoggingEnabled()) {
            mSqLiteDatabase.beginTransactionNonExclusive();
            return;
        }
        mSqLiteDatabase.beginTransaction();
    }

    @Override
    protected void setTransactionSuccessful() {
        mSqLiteDatabase.setTransactionSuccessful();
    }

    @Override
    protected void endTransaction() {
        mSqLiteDatabase.endTransaction();
    }

    @Override
    public boolean execSQL(@NonNull String sql) {
        try {
            mSqLiteDatabase.execSQL(sql);
            return true;
        } catch (SQLException e) {
            Log.e("database", "Error execSQL " + sql, e);
        }
        return false;
    }

    @Override
    public boolean execSQL(@NonNull String sql, @NonNull final Object[] bindArgs) {
        SqlBuilder execSQL = new SqlBuilder().sql(sql, null).whereArgs(bindArgs);
        try {
            mSqLiteDatabase.execSQL(sql, bindArgs);
            return true;
        } catch (SQLException e) {
            Log.e("database", "Error execSQL " + execSQL.toString(), e);
            return false;
        }
    }

    @Nullable
    @Override
    public Cursor rawQueryCursor(@NonNull String sql, @Nullable Object[] selectionArgs) {
        SqlBuilder rawQueryCursor = new SqlBuilder().sql(sql, null).whereArgs(selectionArgs);
        try {
            return mSqLiteDatabase.rawQuery(sql, rawQueryCursor.whereArgs);
        } catch (SQLException e) {
            Log.e("database", "Error rawQueryCursor " + rawQueryCursor.toString(), e);
            return null;
        }
    }

    @Override
    public long insert(@NonNull String table, @Nullable final String nullColumnHack, @NonNull ContentValues values,
                       ConflictAlgorithm conflictAlgorithm) {
        SqlBuilder insert = new SqlBuilder().conflictAlgorithm(conflictAlgorithm)
                .insert(table, values, nullColumnHack, conflictAlgorithm);
        try {
            final long count;
            if ((count = mSqLiteDatabase.insertWithOnConflict(table, nullColumnHack, values, insert.conflictIndex)) <
                    0) {
                Log.w("database", "插入数据出错 " + insert.toString());
            }
            return count;
        } catch (SQLException e) {
            Log.e("database", "Error insert " + insert.toString(), e);
            return -1;
        }
    }

    @Override
    public int update(@NonNull String table, @NonNull ContentValues values, @NonNull String whereClause,
                      @Nullable final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        SqlBuilder update = new SqlBuilder().conflictAlgorithm(conflictAlgorithm)
                .update(table, values, whereClause, whereArgs, conflictAlgorithm);
        try {
            final int count;
            if ((count = mSqLiteDatabase
                    .updateWithOnConflict(table, values, whereClause, update.whereArgs, update.conflictIndex)) < 0) {
                Log.w("database", "更新数据出错 " + update.toString());
            }
            return count;
        } catch (SQLException e) {
            Log.e("database", "Error update " + update.toString(), e);
            return -1;
        }
    }

    @Override
    public int delete(@NonNull String table, @Nullable String whereClause, @Nullable final Object[] whereArgs) {
        SqlBuilder delete = new SqlBuilder().delete(table, whereClause, whereArgs);
        try {
            final int count;
            if ((count = mSqLiteDatabase.delete(table, whereClause, delete.whereArgs)) < 0) {
                Log.w("database", "删除数据出错 " + delete.toString());
            }
            return count;
        } catch (SQLException e) {
            Log.e("database", "Error delete " + delete.toString(), e);
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SQLAndroidStatement compileStatement(@NonNull String sql) {
        return SQLAndroidStatement.createStatement(mSqLiteDatabase, sql);
    }

    @Override
    public void close() {
        mSqLiteDatabase.close();
    }

    @NonNull
    @Override
    public String getPath() {
        return mSqLiteDatabase.getPath();
    }

    @Override
    public int getVersion() {
        return mSqLiteDatabase.getVersion();
    }

    @Override
    public boolean isOpen() {
        return mSqLiteDatabase.isOpen();
    }

    @Override
    public void setVersion(int version) {
        mSqLiteDatabase.setVersion(version);
    }

    @Override
    protected void acquireReference() {
        mSqLiteDatabase.acquireReference();
    }

    @Override
    protected void releaseReference() {
        mSqLiteDatabase.releaseReference();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void releaseReferenceFromContainer() {
        mSqLiteDatabase.releaseReferenceFromContainer();
    }
}
