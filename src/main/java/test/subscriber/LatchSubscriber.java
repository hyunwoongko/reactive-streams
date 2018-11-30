package test.subscriber;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오후 9:08
 * @Homepage : https://github.com/gusdnd852
 */
public class LatchSubscriber<T> extends DelegateSubscriber<T> {
    private final CountDownLatch latch;

    public LatchSubscriber(long backPressure, CountDownLatch latch) {
        super(backPressure);
        this.latch = latch;
    }

    @Override public void onNext(T item) {
        super.onNext(item);
        latch.countDown();
    }
}
