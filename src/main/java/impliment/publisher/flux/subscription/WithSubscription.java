package impliment.publisher.flux.subscription;

import java.util.Iterator;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 7:35
 * @Homepage : https://github.com/gusdnd852
 */
public class WithSubscription<T> implements Flow.Subscription {
    private final Flow.Subscriber<? super T> subscriber;
    private final Iterator<T> iterator;

    public WithSubscription(Flow.Subscriber<? super T> subscriber, Iterable<T> item) {
        this.subscriber = subscriber;
        this.iterator = item.iterator();
    }

    @Override public void request(long n) {
        while (--n > 0) {
            try {
                if (iterator.hasNext()) {
                    subscriber.onNext(iterator.next());
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
