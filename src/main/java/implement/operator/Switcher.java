package implement.operator;

import implement.function.Predicate;
import implement.publisher.Publisher;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-28 오전 6:00
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Switcher<T> extends Publisher<T> {

    private final Publisher<T> flux;
    private final Predicate<T> predicate;

    public Switcher(Publisher<T> flux, Predicate<T> predicate) {
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
                    } else {
                        subscriber.onNext(null);
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
