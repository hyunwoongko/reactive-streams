package implement.operator;

import implement.function.BiFunction;
import implement.publisher.Publisher;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 8:58
 * @Homepage : https://github.com/gusdnd852
 */
public class Reducer<T, R> extends Publisher<R> {

    private final Publisher<T> flux;
    private final BiFunction<R, T, R> biFunction;
    private R temp;

    public Reducer(Publisher<T> flux, R init, BiFunction<R, T, R> biFunction) {
        this.flux = flux;
        this.biFunction = biFunction;
        this.temp = init;
    }

    @Override public void subscribe(Flow.Subscriber<? super R> subscriber) {
        flux.subscribe(new Flow.Subscriber<>() {

            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                try {
                    temp = biFunction.apply(temp, item);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }

            @Override public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override public void onComplete() {
                  subscriber.onNext(temp);
                subscriber.onComplete();
            }
        });
    }
}
