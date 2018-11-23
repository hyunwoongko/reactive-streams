package impements;

import impements.publisher.Flux;
import impements.publisher.Mono;
import impements.subscriber.Subscriber;
import impements.subscription.Subscription;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-24 오전 12:30
 * @Homepage : https://github.com/gusdnd852
 */
public class Main {
    public static void main(String[] args) {
        Subscriber subscriber = new Subscriber() {
            private Subscription subscription;

            @Override public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage() + Thread.currentThread().getName());
            }

            @Override public void onComplete() {
                System.out.println("COMPLETE" + Thread.currentThread().getName());
            }

            @Override public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
            }

            @Override public void onNext(Object input) {
                subscription.request(Long.MAX_VALUE);
            }
        };

        List list = Arrays.asList(7, 8, 9);

        Flux.with(list, 1, 2, 3, 4, 5, 6)
                .<Integer>filter(input -> input > 3)
                .next(input -> System.out.println(input + " : " + Thread.currentThread().getName()))
                .subscribe(subscriber);

        subscriber.onNext("");
    }
}
