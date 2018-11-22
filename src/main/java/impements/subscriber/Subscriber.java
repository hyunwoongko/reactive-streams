package impements.subscriber;


import java.util.concurrent.Executor;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-03 오전 10:31
 * @Homepage : https://github.com/gusdnd852
 */

public interface Subscriber<T, R> extends Flow.Subscriber<T> {

    void onError(Throwable throwable);

    void onComplete();

    void onSubscribe(Flow.Subscription subscription);

    void onNext(T input);

    Boolean onCheck(T input);

    R onMap(T input);

    Executor onFork();

    Boolean onJoin();
}