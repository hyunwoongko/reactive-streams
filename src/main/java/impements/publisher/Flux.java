package impements.publisher;

import impements.function.*;
import impements.protocol.Subscriber;
import impements.subscription.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-23 오후 7:26
 * @Homepage : https://github.com/gusdnd852
 */
public class Flux {
    private ConcurrentLinkedQueue<Subscribable> subscribers = new ConcurrentLinkedQueue<>();
    private Subscription subscription = new Subscription();
    private List<Object> inputs = new ArrayList<>();

    private Flux(Object[] inputs) {
        for (Object input : inputs) {
            if (input instanceof Iterable)
                for (Object once : (Iterable) input)
                    this.inputs.add(once);

            else this.inputs.add(input);
        }
    }

    private Flux() {
    }

    @SafeVarargs
    public static <T> Flux with(T... inputs) {
        return new Flux(inputs);
    }

    public static Flux empty() {
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

    public Flux fork(Forkable subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public Flux join() {
        subscribers.add((Joinable) () -> true);
        return this;
    }

    public void subscribe(Subscriber handler) {
        handler.onSubscribe(subscription.with(inputs)
                .setHandler(handler)
                .setSubscribers(subscribers));
    }
}
