package test.subscription;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오후 3:28
 * @Homepage : https://github.com/gusdnd852
 */
public class MonoSubscription<T> implements Flow.Subscription {
    private final Flow.Subscriber<? super T> subscriber;
    private final T item;

    public MonoSubscription(Flow.Subscriber<? super T> subscriber, T item) {
        this.subscriber = subscriber;
        this.item = item;
    }

    @Override public void request(long n) {
        try {
            subscriber.onNext(item);
            subscriber.onComplete();
        } catch (Throwable t) {
            subscriber.onError(t);
        }
    }

    @Override public void cancel() {
    }
}
