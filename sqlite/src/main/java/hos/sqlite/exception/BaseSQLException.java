package hos.sqlite.exception;

import android.database.SQLException;

/**
 * <p>Title: BaseSQLException </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/12 9:00
 */
public class BaseSQLException extends SQLException {

    public BaseSQLException() {
    }

    public BaseSQLException(String error) {
        super(error);
    }

    public BaseSQLException(String error, Throwable cause) {
        super(error, cause);
    }
}
