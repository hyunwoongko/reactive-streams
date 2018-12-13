package operator;

import java.util.concurrent.Flow;
import java.util.function.Function;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-25 오후 11:53
 * @Homepage : https://github.com/gusdnd852
 */
public class Mapper implements Flow.Publisher {
    Flow.Publisher publisher;
    Function<Integer, Integer> function;

    public Mapper(Flow.Publisher publisher, Function<Integer, Integer> integerIntegerFunction) {
        this.publisher = publisher;
        this.function = integerIntegerFunction;
    }

    @Override public void subscribe(Flow.Subscriber subscriber) {
        publisher.subscribe(new Flow.Subscriber() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(Object item) {
                subscriber.onNext(function.apply((Integer) item));
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
