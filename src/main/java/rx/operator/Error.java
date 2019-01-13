package rx.operator;

import rx.publisher.Publisher;
import utils.function.Consumer;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-28 오전 9:09
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Error<T> extends Publisher<T> {

    private Publisher<T> publisher;
    private Consumer<Throwable> error;

    public Error(Publisher<T> publisher, Consumer<Throwable> error) {
        this.publisher = publisher;
        this.error = error;
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
        publisher.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                subscriber.onNext(item);
            }

            @Override public void onError(Throwable throwable) {
                try {
                    error.accept(throwable);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }

            @Override public void onComplete() {
                subscriber.onComplete();
            }
        });
    }
}
