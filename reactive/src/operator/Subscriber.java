package operator;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-25 오후 11:30
 * @Homepage : https://github.com/gusdnd852
 */
public class Subscriber implements Flow.Subscriber {
    @Override public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("subscribe");
        subscription.request(Long.MAX_VALUE);
    }

    @Override public void onNext(Object item) {
        System.out.println(item + "onNext");
    }

    @Override public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override public void onComplete() {
        System.out.println("complete");
    }
}