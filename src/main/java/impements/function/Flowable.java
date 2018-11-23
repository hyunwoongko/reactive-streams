package impements.function;

import impements.subscription.Subscription;

import java.util.concurrent.Executor;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-03 오전 10:31
 * @Homepage : https://github.com/gusdnd852
 */

@FunctionalInterface
public interface Flowable<T> extends Subscribable<T, Object> {

    @Override
    default void onError(Throwable throwable){}

    @Override
    default void onComplete(){}

    @Override
    default void onSubscribe(Subscription subscription) {}

    @Override
    default Boolean onCheck(T input)  {return null;}

    @Override
    default Object onMap(T input)  {return null;}

    @Override
    void onNext(T input);

    @Override
    default Executor onFork()  {return null;}

    @Override
    default Boolean onJoin(){return null;}
}