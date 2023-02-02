package hos.sqlite;

import android.database.SQLException;



import hos.sqlite.statement.SQLiteStatement;

/**
 * <p>Title: SQLAndroidStatement </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/19 18:51
 */
final class SQLAndroidStatement extends SQLiteStatement<SQLAndroidStatement> {

    private final android.database.sqlite.SQLiteStatement mSqLiteStatement;

    static SQLAndroidStatement createStatement( final android.database.sqlite.SQLiteDatabase sqLiteDatabase,
                                                final String sql) {
        return new SQLAndroidStatement(sqLiteDatabase, sql);
    }

    SQLAndroidStatement(android.database.sqlite.SQLiteDatabase sqLiteDatabase, String sql) {
        mSqLiteStatement = sqLiteDatabase.compileStatement(sql);
    }

    @Override
    public void execute() throws SQLException {
        mSqLiteStatement.execute();
    }

    @Override
    public int executeUpdateDelete() throws SQLException {
        return mSqLiteStatement.executeUpdateDelete();
    }

    @Override
    public long executeInsert() throws SQLException {
        return mSqLiteStatement.executeInsert();
    }

    @Override
    public void close() {
        mSqLiteStatement.close();
    }

    @Override
    public SQLAndroidStatement bindNull(int index) {
        mSqLiteStatement.bindNull(index);
        return this;
    }

    @Override
    public SQLAndroidStatement bindLong(int index, long value) {
        mSqLiteStatement.bindLong(index, value);
        return this;
    }

    @Override
    public SQLAndroidStatement bindDouble(int index, double value) {
        mSqLiteStatement.bindDouble(index, value);
        return this;
    }

    @Override
    public SQLAndroidStatement bindString(int index, String value) {
        mSqLiteStatement.bindString(index, value);
        return this;
    }

    @Override
    public SQLAndroidStatement bindBlob(int index, byte[] value) {
        mSqLiteStatement.bindBlob(index, value);
        return this;
    }

    @Override
    public void clearBindings() {
        mSqLiteStatement.clearBindings();
    }

    @Override
    public void acquireReference() {
        mSqLiteStatement.acquireReference();
    }

    @Override
    protected void releaseReference() {
        mSqLiteStatement.releaseReference();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void releaseReferenceFromContainer() {
        mSqLiteStatement.releaseReferenceFromContainer();
    }

    
    @Override
    public String toString() {
        return mSqLiteStatement.toString();
    }
}
