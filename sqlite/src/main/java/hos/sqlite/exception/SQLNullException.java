package hos.sqlite.exception;

/**
 * <p>Title: SQLNullException </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/12 9:01
 */
public class SQLNullException extends BaseSQLException {
    public SQLNullException(String error) {
        super(error);
    }

    public SQLNullException(String error, Throwable cause) {
        super(error, cause);
    }
}
