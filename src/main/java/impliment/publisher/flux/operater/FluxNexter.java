package impliment.publisher.flux.operater;

import impliment.publisher.flux.Flux;

import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 11:23
 * @Homepage : https://github.com/gusdnd852
 */
public class FluxNexter<T> extends Flux<T> {
    private Flux<T> flux;
    private Consumer<T> consumer;

    public FluxNexter(Flux<T> flux, Consumer<T> consumer) {
        this.flux = flux;
        this.consumer = consumer;
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
        flux.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                consumer.accept(item);
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
