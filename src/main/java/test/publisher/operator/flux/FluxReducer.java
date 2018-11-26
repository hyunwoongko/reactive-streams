package test.publisher.operator.flux;

import test.publisher.Flux;

import java.util.concurrent.Flow;
import java.util.function.BiFunction;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 8:58
 * @Homepage : https://github.com/gusdnd852
 */
public class FluxReducer<T, R> extends Flux<R> {

    private final Flux<T> flux;
    private final BiFunction<R, T, R> biFunction;
    private final R init;

    public FluxReducer(Flux<T> flux, R init, BiFunction<R, T, R> biFunction) {
        this.flux = flux;
        this.biFunction = biFunction;
        this.init = init;
    }

    @Override public void subscribe(Flow.Subscriber<? super R> subscriber) {
        flux.subscribe(new Flow.Subscriber<>() {
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
