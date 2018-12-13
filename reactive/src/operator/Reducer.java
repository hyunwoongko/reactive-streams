package operator;

import java.util.concurrent.Flow;
import java.util.function.BiFunction;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 6:52
 * @Homepage : https://github.com/gusdnd852
 */
public class Reducer<T> implements Flow.Publisher<T> {

    Flow.Publisher<T> publisher;
    BiFunction<T, T, T> biFunction;
    T init;

    public Reducer(Flow.Publisher<T> publisher, T init, BiFunction<T, T, T> biFunction) {
        this.publisher = publisher;
        this.biFunction = biFunction;
        this.init = init;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        publisher.subscribe(new Flow.Subscriber<T>() {
            T res = init;

            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                res = biFunction.apply(item, res);
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
