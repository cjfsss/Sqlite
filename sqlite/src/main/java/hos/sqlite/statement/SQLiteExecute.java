package hos.sqlite.statement;

import android.database.SQLException;

import java.io.Closeable;
import java.io.IOException;

/**
 * <p>Title: SQLiteStatement </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/19 18:48
 */
public abstract class SQLiteExecute extends SQLiteClosable implements Closeable {


    public abstract void execute() throws SQLException;

    public abstract int executeUpdateDelete() throws SQLException;

    public abstract long executeInsert() throws SQLException;

    public abstract void close() throws IOException;
}
