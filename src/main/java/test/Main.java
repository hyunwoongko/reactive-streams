package test;

import test.publisher.Flux;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오후 4:29
 * @Homepage : https://github.com/gusdnd852
 */
public class Main {
    public static void main(String[] args) {
        Flux.with(1, 2, 3, 4, 5)
                .map(integer -> integer * 2)
                .filter(integer -> integer > 2)
                .subscribeOn()
                .reduce(0, (integer, integer2) -> integer + integer2)
                .subscribe(new Flow.Subscriber<>() {
                    @Override public void onSubscribe(Flow.Subscription subscription) {
                        System.out.println("sub");
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override public void onNext(Integer item) {
                        System.out.println(item);
                    }

                    @Override public void onError(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }

                    @Override public void onComplete() {
                        System.out.println("com");
                    }
                });
    }
}
