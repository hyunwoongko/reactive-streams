package impements.subscriber;

import impements.subscription.Subscription;

import java.util.concurrent.Executor;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-03 오전 10:31
 * @Homepage : https://github.com/gusdnd852
 */


@FunctionalInterface
public interface Joinable extends Subscribable {

    @Override
    default void onError(Throwable throwable){}

    @Override
    default void onComplete(){}

    @Override
    default void onSubscribe(Subscription subscription) {}

    @Override
    default Boolean onCheck(Object input) {return null;}

    @Override
    default Object onMap(Object input) {return null;}

    @Override
    default void onNext(Object input) {}

    @Override
    Boolean onJoin();

    @Override
    default Executor onFork()  {return null;}
}
