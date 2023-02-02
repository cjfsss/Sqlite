package hos.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;


import hos.sqlite.datebase.ConflictAlgorithm;
import hos.sqlite.datebase.SqlBuilder;
import hos.sqlite.exception.SQLNullException;
import hos.sqlite.statement.SQLiteDatabase;
import hos.utils.SqliteLog;

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

    private SQLAndroidDatabase() {
    }

    static SQLAndroidDatabase create() {
        return new SQLAndroidDatabase();
    }

    static SQLiteDatabase open(final File file,
                               final android.database.sqlite.SQLiteDatabase.CursorFactory factory) throws SQLNullException {
        return SQLAndroidDatabase.create().openOrCreateDatabase(file, factory);
    }

    static SQLiteDatabase open(android.database.sqlite.SQLiteDatabase database) throws SQLNullException {
        return SQLAndroidDatabase.create().openOrCreateDatabase(database);
    }

    SQLiteDatabase openOrCreateDatabase(final File file,
                                        final android.database.sqlite.SQLiteDatabase.CursorFactory factory) throws SQLNullException {
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
            SqliteLog.e("数据库打开异常：" + "\n" + file.getAbsolutePath() + "\t\t找不到文件\n" + e.getMessage());
            throw new SQLNullException("Database File create Failed " + file.getAbsolutePath(), e);
        }
        SqliteLog.d("getConnection successful .");
        return this;
    }


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
    public boolean execSQL(String sql) {
        try {
            SqliteLog.d(sql);
            mSqLiteDatabase.execSQL(sql);
            return true;
        } catch (SQLException e) {
            SqliteLog.e("Error execSQL " + sql, e);
        }
        return false;
    }

    @Override
    public boolean execSQL(String sql, final Object[] bindArgs) {
        SqlBuilder sqlBuilder = new SqlBuilder().sql(sql, null).whereArgs(bindArgs);
        try {
            SqliteLog.d(sqlBuilder.toString());
            mSqLiteDatabase.execSQL(sql, bindArgs);
            return true;
        } catch (SQLException e) {
            SqliteLog.e("Error execSQL " + sqlBuilder.toString(), e);
            return false;
        }
    }


    @Override
    public Cursor rawQueryCursor(String sql, Object[] selectionArgs) {
        SqlBuilder sqlBuilder = new SqlBuilder().sql(sql, null).whereArgs(selectionArgs);
        try {
            SqliteLog.d(sqlBuilder.toString());
            return mSqLiteDatabase.rawQuery(sql, sqlBuilder.whereArgs);
        } catch (SQLException e) {
            SqliteLog.e("Error rawQueryCursor " + sqlBuilder.toString(), e);
            return null;
        }
    }

    @Override
    public long insert(String table, final String nullColumnHack, ContentValues values,
                       ConflictAlgorithm conflictAlgorithm) {
        SqlBuilder sqlBuilder = new SqlBuilder().conflictAlgorithm(conflictAlgorithm)
                .insert(table, values, nullColumnHack, conflictAlgorithm);
        try {
            SqliteLog.d(sqlBuilder.toString());
            final long count;
            if ((count = mSqLiteDatabase.insertWithOnConflict(table, nullColumnHack, values, sqlBuilder.conflictIndex)) <
                    0) {
                SqliteLog.e( "插入数据出错 " + sqlBuilder.toString());
            }
            return count;
        } catch (SQLException e) {
            SqliteLog.e("Error insert " + sqlBuilder.toString(), e);
            return -1;
        }
    }

    @Override
    public int update(String table, ContentValues values, String whereClause,
                      final Object[] whereArgs, ConflictAlgorithm conflictAlgorithm) {
        SqlBuilder sqlBuilder = new SqlBuilder().conflictAlgorithm(conflictAlgorithm)
                .update(table, values, whereClause, whereArgs, conflictAlgorithm);
        try {
            SqliteLog.d(sqlBuilder.toString());
            final int count;
            if ((count = mSqLiteDatabase
                    .updateWithOnConflict(table, values, whereClause, sqlBuilder.whereArgs, sqlBuilder.conflictIndex)) < 0) {
                SqliteLog.e( "插入数据出错 " + sqlBuilder.toString());
            }
            return count;
        } catch (SQLException e) {
            SqliteLog.e("Error update " + sqlBuilder.toString(), e);
            return -1;
        }
    }

    @Override
    public int delete(String table, String whereClause, final Object[] whereArgs) {
        SqlBuilder sqlBuilder = new SqlBuilder().delete(table, whereClause, whereArgs);
        try {
            SqliteLog.d(sqlBuilder.toString());
            final int count;
            if ((count = mSqLiteDatabase.delete(table, whereClause, sqlBuilder.whereArgs)) < 0) {
                SqliteLog.e( "插入数据出错 " + sqlBuilder.toString());
            }
            return count;
        } catch (SQLException e) {
            SqliteLog.e("Error delete " + sqlBuilder.toString(), e);
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SQLAndroidStatement compileStatement(String sql) {
        return SQLAndroidStatement.createStatement(mSqLiteDatabase, sql);
    }

    @Override
    public void close() {
        mSqLiteDatabase.close();
    }


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
