package test.publisher.operator.mono;

import test.publisher.Mono;

import java.util.concurrent.Flow;
import java.util.function.BiFunction;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 8:58
 * @Homepage : https://github.com/gusdnd852
 */
public class MonoReducer<T, R> extends Mono<R> {

    private final Mono<T> mono;
    private final BiFunction<R, T, R> biFunction;
    private final R init;

    public MonoReducer(Mono<T> mono, R init, BiFunction<R, T, R> biFunction) {
        this.mono = mono;
        this.biFunction = biFunction;
        this.init = init;
    }

    @Override public void subscribe(Flow.Subscriber<? super R> subscriber) {
        mono.subscribe(new Flow.Subscriber<>() {
            R res = init;

            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                res = biFunction.apply(res, item);
            }

            @Override public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override public void onComplete() {
                subscriber.onNext(res);
                subscriber.onComplete();
            }
        });
    }
}
