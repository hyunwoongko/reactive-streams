package utils.function;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-30 오전 10:47
 * @Homepage : https://github.com/gusdnd852
 */
@FunctionalInterface
public interface Consumer<T> {
    void accept(T input) throws Exception;
}
