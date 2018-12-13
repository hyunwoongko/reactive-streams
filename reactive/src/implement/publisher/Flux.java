package implement.publisher;

import implement.scheduler.Scheduler;
import implement.subscription.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-12-03 오후 12:30
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Flux<T> extends Publisher<T> {
    public static <T> Flux<T> main(T... inputs) {
        List<T> inputAsList = new ArrayList<>();
        for (T input : inputs) {
            if (input instanceof Iterable)
                for (T once : (Iterable<T>) input)
                    inputAsList.add(once);
            else inputAsList.add(input);
        }
        return new Flux<>() {
            @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
                Scheduler.main()
                        .execute(() -> subscriber.onSubscribe(new Subscription<>(subscriber, inputAsList)));
            }
        };
    }

    public static <T> Flux<T> background(T... inputs) {
        List<T> inputAsList = new ArrayList<>();
        for (T input : inputs) {
            if (input instanceof Iterable)
                for (T once : (Iterable<T>) input)
                    inputAsList.add(once);
            else inputAsList.add(input);
        }
        return new Flux<>() {
            @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
                Scheduler.background()
                        .execute(() -> subscriber.onSubscribe(new Subscription<>(subscriber, inputAsList)));
            }
        };
    }
}
