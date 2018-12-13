package implement.operator;

import implement.publisher.Publisher;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-28 오전 9:09
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Complete<T> extends Publisher<T> {

    private Runnable runnable;
    private Publisher<T> publisher;

    public Complete(Publisher<T> publisher, Runnable runnable) {
        this.runnable = runnable;
        this.publisher = publisher;
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
        publisher.subscribe(new Flow.Subscriber<T>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override public void onNext(T item) {
                subscriber.onNext(item);

            }

            @Override public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override public void onComplete() {
                runnable.run();
            }
        });
    }
}
