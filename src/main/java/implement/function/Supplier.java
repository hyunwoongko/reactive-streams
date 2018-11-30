package implement.function;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-30 오전 10:48
 * @Homepage : https://github.com/gusdnd852
 */
@FunctionalInterface
public interface Supplier<T> {
    T get() throws Exception;
}
