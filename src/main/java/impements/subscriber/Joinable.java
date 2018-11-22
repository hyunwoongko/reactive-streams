package impements.subscriber;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-03 오전 10:31
 * @Homepage : https://github.com/gusdnd852
 */


@FunctionalInterface
public interface Joinable extends Subscriber {

    @Override
    default void onError(Throwable throwable){}

    @Override
    default void onComplete(){}

    @Override
    default void onSubscribe(Flow.Subscription subscription) {}

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
