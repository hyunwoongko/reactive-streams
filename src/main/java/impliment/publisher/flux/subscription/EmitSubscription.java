package impliment.publisher.flux.subscription;

import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 11:26
 * @Homepage : https://github.com/gusdnd852
 */
public class EmitSubscription<T> implements Flow.Subscription {
    private final Flow.Subscriber<? super T> subscriber;
    private final Consumer<Flow.Subscriber<? super T>> consumer;

    public EmitSubscription(Flow.Subscriber<? super T> subscriber, Consumer<Flow.Subscriber<? super T>> consumer) {
        this.subscriber = subscriber;
        this.consumer = consumer;
    }

    @Override public void request(long n) {
        try {
            consumer.accept(subscriber);
            subscriber.onComplete();
        } catch (Throwable t) {
            subscriber.onError(t);
        }
    }

    @Override public void cancel() {

    }
}
