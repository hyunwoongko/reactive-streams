package test.publisher.operator.flux;

import test.publisher.Flux;

import java.util.concurrent.Flow;
import java.util.function.Function;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 7:22
 * @Homepage : https://github.com/gusdnd852
 */
public class FluxMapper<T, R> extends Flux<R> {

    private final Flux<T> flux;
    private final Function<T, R> function;

    public FluxMapper(Flux<T> flux, Function<T, R> function) {
        this.flux = flux;
        this.function = function;
    }

    @Override public void subscribe(Flow.Subscriber<? super R> subscriber) {
        flux.subscribe(new Flow.Subscriber<>() {
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
