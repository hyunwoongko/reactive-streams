package rx.operator;

import rx.publisher.Publisher;

import java.util.concurrent.Flow;
import java.util.function.Predicate;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 9:05
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Filter<T> extends Publisher<T> {

    private final Predicate<T> predicate;
    private final Publisher<T> flux;

    public Filter(Publisher<T> flux, Predicate<T> predicate) {
        this.flux = flux;
        this.predicate = predicate;
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
        flux.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                try {
                    if (predicate.test(item)) {
                        subscriber.onNext(item);
                    }
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
