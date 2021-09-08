package hos.sqlite.exception;

/**
 * <p>Title: SqlArgumentError </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/15 13:26
 */
public class SQLArgumentException extends BaseSQLException {

    public SQLArgumentException(String error) {
        super(error);
    }

    public SQLArgumentException(String error, Throwable cause) {
        super(error, cause);
    }
}
