package operator;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-25 오후 11:29
 * @Homepage : https://github.com/gusdnd852
 */
public class Publisher implements Flow.Publisher {
    private Iterable input;

    public Publisher(Iterable input) {
        this.input = input;
    }

    @Override public void subscribe(Flow.Subscriber subscriber) {
        subscriber.onSubscribe(new Flow.Subscription() {
            @Override public void request(long n) {
                try {
                    input.forEach(subscriber::onNext);
                    subscriber.onComplete();
                } catch (Throwable throwable) {
                    subscriber.onError(throwable);
                }
            }

            @Override public void cancel() {

            }
        });
    }
}
