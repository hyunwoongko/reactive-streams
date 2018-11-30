package test.publisher.operator.mono;

import test.publisher.Mono;

import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 11:23
 * @Homepage : https://github.com/gusdnd852
 */
public class MonoNexter<T> extends Mono<T> {
    private Mono<T> mono;
    private Consumer<T> consumer;

    public MonoNexter(Mono<T> mono, Consumer<T> consumer) {
        this.mono = mono;
        this.consumer = consumer;
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
        mono.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                subscriber.onNext(item);
                consumer.accept(item);
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
