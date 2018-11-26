package test.publisher.operator.mono;

import test.publisher.Mono;

import java.util.concurrent.Flow;
import java.util.function.Function;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 7:22
 * @Homepage : https://github.com/gusdnd852
 */
public class MonoMapper<T, R> extends Mono<R> {

    private final Mono<T> mono;
    private final Function<T, R> function;

    public MonoMapper(Mono<T> mono, Function<T, R> function) {
        this.mono = mono;
        this.function = function;
    }

    @Override public void subscribe(Flow.Subscriber<? super R> subscriber) {
        mono.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                subscriber.onNext(function.apply(item));
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
