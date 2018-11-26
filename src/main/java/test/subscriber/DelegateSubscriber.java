package test.subscriber;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 9:29
 * @Homepage : https://github.com/gusdnd852
 */
public class DelegateSubscriber<T> implements Flow.Subscriber<T> {

    private final long backPressure;

    public DelegateSubscriber(long backPressure) {
        this.backPressure = backPressure;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(backPressure);
    }

    @Override
    public void onNext(T item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
