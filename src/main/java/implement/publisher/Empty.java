package implement.publisher;

import implement.scheduler.Scheduler;
import implement.subscription.Subscription;

import java.util.Collections;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-12-03 오후 12:19
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Empty<T> extends Publisher<T> {
    public static <T> Empty<T> main() {
        return new Empty<>() {
            @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
                Scheduler.main()
                        .execute(() -> subscriber.onSubscribe(new Subscription<>(subscriber, Collections.singletonList((T) "EMPTY INPUT"))));
            }
        };
    }

    public static <T> Empty<T> background() {
        return new Empty<>() {
            @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
                Scheduler.background()
                        .execute(() -> subscriber.onSubscribe(new Subscription<>(subscriber, Collections.singletonList((T) "EMPTY INPUT"))));
            }
        };
    }
}
