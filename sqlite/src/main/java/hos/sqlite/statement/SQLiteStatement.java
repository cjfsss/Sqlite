package hos.sqlite.statement;

/**
 * <p>Title: SQLiteProgram </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/19 18:59
 */
public abstract class SQLiteStatement<STATEMENT extends SQLiteStatement<STATEMENT>> extends SQLiteExecute {

    /**
     * Bind a NULL value to this statement. The value remains bound until
     * {@link #clearBindings} is called.
     *
     * @param index The 1-based index to the parameter to bind null to
     */
    public abstract STATEMENT bindNull(int index);

    /**
     * Bind a long value to this statement. The value remains bound until
     * {@link #clearBindings} is called.
     * addToBindArgs
     *
     * @param index The 1-based index to the parameter to bind
     * @param value The value to bind
     */
    public abstract STATEMENT bindLong(int index, long value);

    /**
     * Bind a double value to this statement. The value remains bound until
     * {@link #clearBindings} is called.
     *
     * @param index The 1-based index to the parameter to bind
     * @param value The value to bind
     */
    public abstract STATEMENT bindDouble(int index, double value);

    /**
     * Bind a String value to this statement. The value remains bound until
     * {@link #clearBindings} is called.
     *
     * @param index The 1-based index to the parameter to bind
     * @param value The value to bind, must not be null
     */
    public abstract STATEMENT bindString(int index, String value);

    /**
     * Bind a byte array value to this statement. The value remains bound until
     * {@link #clearBindings} is called.
     *
     * @param index The 1-based index to the parameter to bind
     * @param value The value to bind, must not be null
     */
    public abstract STATEMENT bindBlob(int index, byte[] value);

    /**
     * Clears all existing bindings. Unset bindings are treated as NULL.
     */
    public abstract void clearBindings();


}
