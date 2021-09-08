package hos.sqlite.datebase;

import androidx.annotation.NonNull;

/**
 * <p>Title: Database </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/17 16:29
 */
public interface Database extends DatabaseExecutor {

    /// The path of the database
    @NonNull
    default String getPath() {
        return getConnection().getPath();
    }

    /// Close the database. Cannot be accessed anymore
    default void close() {
        getConnection().close();
    }

    ///
    /// Get the database inner version
    ///
    default int getVersion() {
        return getConnection().getVersion();
    }

    /// Tell if the database is open, returns false once close has been called
    default boolean isOpen() {
        return getConnection().isOpen();
    }

    ///
    /// Set the database inner version
    /// Used internally for open helpers and automatic versioning
    ///
    default void setVersion(int version) {
        getConnection().setVersion(version);
    }
}
