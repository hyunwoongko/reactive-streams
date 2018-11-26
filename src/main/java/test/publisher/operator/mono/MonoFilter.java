package test.publisher.operator.mono;

import test.publisher.Mono;

import java.util.concurrent.Flow;
import java.util.function.Predicate;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 9:05
 * @Homepage : https://github.com/gusdnd852
 */
public class MonoFilter<T> extends Mono<T> {

    private final Predicate<T> predicate;
    private final Mono<T> mono;

    public MonoFilter(Mono<T> mono, Predicate<T> predicate) {
        this.mono = mono;
        this.predicate = predicate;
    }


    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
        mono.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                if (predicate.test(item))
                    subscriber.onNext(item);
            }

            @Override public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override public void onComplete() {
                subscriber.onComplete();
            }
        });
    }
}
