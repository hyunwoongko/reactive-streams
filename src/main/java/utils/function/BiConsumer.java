package utils.function;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-30 오전 10:52
 * @Homepage : https://github.com/gusdnd852
 */
@FunctionalInterface
public interface BiConsumer<T, U> {
    void accept(T input1, U input2) throws Exception;
}
