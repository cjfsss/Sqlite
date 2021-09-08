package hos.sqlite.statement;

/**
 * <p>Title: SQLiteClosable </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/19 10:50
 */
public abstract class SQLiteClosable {

    protected abstract void acquireReference();

    protected abstract void releaseReference();

    protected abstract void releaseReferenceFromContainer();
}
