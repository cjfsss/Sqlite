package hos.utils;

import android.util.Log;

import hos.sqlite.BuildConfig;

/**
 * <p>Title: SqliteLog </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2023-02-02 13:51
 */
public class SqliteLog {

    private static final String TAG = "Sqlite";

    public static int w(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            return Log.w(TAG, throwable);
        }
        return -1;
    }

    public static int d(String msg) {
        if (BuildConfig.DEBUG) {
            return Log.d(TAG, msg);
        }
        return -1;
    }


    public static int e(String msg) {
        if (BuildConfig.DEBUG) {
            return Log.e(TAG, msg);
        }
        return -1;
    }

    public static int e(String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.e(TAG, msg, tr);
        }
        return -1;
    }
}
