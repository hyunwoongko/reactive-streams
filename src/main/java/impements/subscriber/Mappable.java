package impements.subscriber;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-03 오전 10:31
 * @Homepage : https://github.com/gusdnd852
 */

@FunctionalInterface
public interface Mappable<T, R> extends Subscriber<T, R> {

    @Override
    default void onError(Throwable throwable){}

    @Override
    default void onComplete(){}

    @Override
    default void onSubscribe(Flow.Subscription subscription) {}

    @Override
    default Boolean onCheck(T input)  {return null;}

    @Override
    default void onNext(T input)  {}

    @Override
    R onMap(T input);

    @Override
    default Executor onFork()  {return null;}

    @Override
    default Boolean onJoin(){return null;}
}