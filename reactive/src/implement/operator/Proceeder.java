package implement.operator;

import implement.function.Consumer;
import implement.publisher.Publisher;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 11:23
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Proceeder<T> extends Publisher<T> {
    private Publisher<T> flux;
    private Consumer<T> consumer;

    public Proceeder(Publisher<T> flux, Consumer<T> consumer) {
        this.flux = flux;
        this.consumer = consumer;
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
        flux.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                try {
                    consumer.accept(item);
                      subscriber.onNext(item);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
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
