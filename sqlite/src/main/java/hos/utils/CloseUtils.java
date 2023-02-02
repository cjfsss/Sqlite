package hos.utils;


import java.io.Closeable;
import java.io.Flushable;

/**
 * <p>Title: IOUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2019/12/27 17:33
 */
public class CloseUtils {

    public static void closeQuietly(Closeable... closeable) {
        if (closeable == null) return;
        for (Closeable close : closeable) {
            try {
                close.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void flushQuietly(Flushable... flushable) {
        if (flushable == null) return;
        for (Flushable flush : flushable) {
            try {
                flush.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
