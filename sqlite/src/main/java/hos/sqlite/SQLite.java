package hos.sqlite;

import androidx.annotation.NonNull;

import java.io.Closeable;

/**
 * <p>Title: SQLite </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/19 19:10
 */
public interface SQLite extends Closeable {


    void close();

    /// The path of the database
    @NonNull
    String getPath();

    ///
    /// Get the database inner version
    ///
    int getVersion();

    /// Tell if the database is open, returns false once close has been called
    boolean isOpen();

    ///
    /// Set the database inner version
    /// Used internally for open helpers and automatic versioning
    ///
    void setVersion(int version);

}
