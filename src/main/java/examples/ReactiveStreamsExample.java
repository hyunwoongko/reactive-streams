package examples;

import rx.publisher.Publisher;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2019-01-14 오전 12:31
 * @Homepage : https://github.com/gusdnd852
 */
public class ReactiveStreamsExample {
    public static void main(String[] args) {
        Publisher.main(1, 2, 3, 4, 5)
                .filter(number -> number > 2)
                .map(number -> number * 2)
                .subscribe(new Flow.Subscriber<>() {
                    @Override public void onSubscribe(Flow.Subscription subscription) {subscription.request(Long.MAX_VALUE);}
                    @Override public void onNext(Integer item) {System.out.println(item + " : " + Thread.currentThread().getName());}
                    @Override public void onError(Throwable throwable) {}
                    @Override public void onComplete() {}
                });
    }
}
