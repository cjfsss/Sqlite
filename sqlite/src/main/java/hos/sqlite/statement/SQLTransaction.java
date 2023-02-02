package hos.sqlite.statement;


import hos.sqlite.datebase.Function;

import hos.sqlite.datebase.SqlBuilder;


/**
 * <p>Title: SQLTransaction </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/6/23 10:10
 */
public interface SQLTransaction {

    default long statement(final String sql,
                           final Object[] whereArgs,
                           final Function<SQLiteStatement<?>, Long> function) {
        Function<SQLiteStatement<?>, Long> sqLiteStatementLongFunction = statement -> {
            new SqlBuilder().statement(statement, whereArgs);
            return function.apply(statement);
        };
        return statement(sql, sqLiteStatementLongFunction);
    }

    long statement(final String sql,
                   final Function<SQLiteStatement<?>, Long> function);

    default long transaction(final String sql, final Object[] whereArgs,
                             final Function<SQLiteStatement<?>, Long> function) {
        Function<SQLiteStatement<?>, Long> transaction = statement -> {
            new SqlBuilder().statement(statement, whereArgs);
            return function.apply(statement);
        };
        return transaction(sql, transaction);
    }

    long transaction(final String sql, final Function<SQLiteStatement<?>, Long> function);

    long transaction(final Function<SQLiteDatabase, Long> function);
}
