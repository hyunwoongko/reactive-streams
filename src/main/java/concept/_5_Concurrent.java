package concept;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class _5_Concurrent {
    public static void main(String[] args) {
        Publisher<Integer> publisher = subscriber -> {
            subscriber.onSubscribe(new Subscription() {
                @Override public void request(long n) {
                    System.out.println(n);
                }

                @Override public void cancel() {

                }
            });
        };

        Subscriber<Integer> subscriber = new Subscriber<>() {
            Subscription subscription;

            @Override public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
            }

            @Override public void onNext(Integer item) {
                subscription.request(item);
            }

            @Override public void onError(Throwable throwable) {

            }

            @Override public void onComplete() {

            }
        };

        publisher.subscribe(subscriber);

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            subscriber.onNext(123);
        });
        service.shutdown();
        subscriber.onNext(12444);
    }
}
