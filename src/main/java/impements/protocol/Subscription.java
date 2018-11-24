package impements.protocol;

/**
 * @Author : Doug Lea
 * @Weaver : Hyunwoong
 * @When : 2018-11-23 오후 9:56
 * @Homepage : https://github.com/gusdnd852
 */
public interface Subscription {
    void request(); // 배압 구현 못함.

    void cancel();
}
