package rx.operator;

import rx.publisher.Publisher;
import utils.function.Function;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 7:22
 * @Homepage : https://github.com/gusdnd852
 */
public class Flatter<T, R> extends Publisher<Publisher<R>> {

    private final Publisher<T> flux;
    private final Function<T, Publisher<R>> function;

    public Flatter(Publisher<T> flux, Function<T, Publisher<R>> function) {
        this.flux = flux;
        this.function = function;
    }

    @Override public void subscribe(Flow.Subscriber<? super Publisher<R>> subscriber) {
        flux.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                try {
                     subscriber.onNext(function.apply(item));
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
