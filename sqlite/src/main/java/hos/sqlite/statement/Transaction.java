package hos.sqlite.statement;

/**
 * <p>Title: Transaction </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/22 16:26
 */
public abstract class Transaction extends SQLiteClosable {

    public abstract boolean enableWriteAheadLogging();

    public abstract void disableWriteAheadLogging();

    protected abstract void beginTransaction();

    protected abstract void setTransactionSuccessful();

    protected abstract void endTransaction();
}
