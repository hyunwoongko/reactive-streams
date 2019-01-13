package rx.subscription;

import java.util.Iterator;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 7:35
 * @Homepage : https://github.com/gusdnd852
 */
public class Subscription<T> implements Flow.Subscription {
    private final Flow.Subscriber<? super T> subscriber;
    private final Iterator<T> iterator;

    public Subscription(Flow.Subscriber<? super T> subscriber, Iterable<T> item) {
        this.subscriber = subscriber;
        this.iterator = item.iterator();
    }

    @Override public void request(long n) {
        while (n-- > 0) { // 배압 처리
            try {
                if (iterator.hasNext()) {
                    T next = iterator.next();
                    subscriber.onNext(next);
                } else {
                    subscriber.onComplete();
                    break;
                }
            } catch (Throwable t) {
                subscriber.onError(t);
            }
        }
    }

    @Override public void cancel() {
        if (iterator != null)
            while (iterator.hasNext()) {
                iterator.remove();
            }
    }
}