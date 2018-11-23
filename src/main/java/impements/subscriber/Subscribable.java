package impements.subscriber;


import impements.subscription.Subscription;

import java.util.concurrent.Executor;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-03 오전 10:31
 * @Homepage : https://github.com/gusdnd852
 */

public interface Subscribable<T, R> extends Subscriber<T> {

    @Override
    void onError(Throwable throwable);

    @Override
    void onComplete();

    @Override
    void onSubscribe(Subscription subscription);

    @Override
    void onNext(T input);

    Boolean onCheck(T input);

    R onMap(T input);

    Executor onFork();

    Boolean onJoin();
}