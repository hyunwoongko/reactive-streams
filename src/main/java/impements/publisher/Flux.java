package impements.publisher;

import impements.subscription.Subscription;
import impements.subscriber.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-23 오후 7:26
 * @Homepage : https://github.com/gusdnd852
 */
public class Flux implements Flow.Publisher {
    private ConcurrentLinkedQueue<Subscriber> subscribers = new ConcurrentLinkedQueue<>();
    private Subscription subscription = new Subscription();
    protected List<Object> inputs;

    private Flux(Object[] inputs) {
        this.inputs = Arrays.asList(inputs);
    }

    private Flux() {
    }

    @SafeVarargs
    public static <T> Flux with(T... inputs) {
        return new Flux(inputs);
    }

    public static Flux just() {
        return new Flux();
    }

    public <T> Flux switchNext(Predicatable<T> predicatable,
                               Flowable<T> TRUE,
                               Flowable<T> FALSE) {
        subscribers.add((Flowable<T>) input -> {
            if (predicatable.onCheck(input)) TRUE.onNext(input);
            else FALSE.onNext(input);
        });
        return this;
    }

    public <T> Flux switchMap(Predicatable<T> predicatable,
                              Mappable<T, Object> TRUE,
                              Mappable<T, Object> FALSE) {
        subscribers.add((Mappable<T, Object>) input -> {
            if (predicatable.onCheck(input)) return TRUE.onMap(input);
            else return FALSE.onMap(input);
        });
        return this;
    }

    public <T> Flux next(Flowable<T> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public <T> Flux map(Mappable<T, Object> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public <T> Flux filter(Predicatable<T> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    @Override
    public void subscribe(Flow.Subscriber handler) {
        handler.onSubscribe(subscription.with(inputs)
                .setHandler(handler)
                .setSubscribers(subscribers));
    }
}
