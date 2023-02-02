package hos.sqlite.datebase;

/**
 * <p>Title: Function </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2023-02-02 11:22
 */
public interface Function<I, O> {
 /**
  * Applies this function to the given input.
  *
  * @param input the input
  * @return the function result.
  */
 O apply(I input);
}
