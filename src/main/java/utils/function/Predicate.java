package utils.function;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-30 오전 10:51
 * @Homepage : https://github.com/gusdnd852
 */
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T input) throws Exception;
}
