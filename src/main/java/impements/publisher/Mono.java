package impements.publisher;

import impements.subscription.Subscription;
import impements.subscriber.*;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-22 오후 8:56
 * @Homepage : https://github.com/gusdnd852
 */

@SuppressWarnings("unchecked")
public class Mono implements Flow.Publisher {

    private ConcurrentLinkedQueue<Subscriber> subscribers = new ConcurrentLinkedQueue<>();
    private Subscription subscription = new Subscription();
    protected Object input;

    protected <T> Mono(T input) {
        this.input = input;
    }

    protected Mono() {
    }

    public static <T> Mono with(T input) {
        return new Mono(input);
    }

    public static Mono just() {
        return new Mono();
    }

    public <T> Mono switchNext(Predicatable<T> predicatable,
                               Flowable<T> TRUE,
                               Flowable<T> FALSE) {
        subscribers.add((Flowable<T>) input -> {
            if (predicatable.onCheck(input)) TRUE.onNext(input);
            else FALSE.onNext(input);
        });
        return this;
    }

    public <T> Mono switchMap(Predicatable<T> predicatable,
                              Mappable<T, Object> TRUE,
                              Mappable<T, Object> FALSE) {
        subscribers.add((Mappable<T, Object>) input -> {
            if (predicatable.onCheck(input)) return TRUE.onMap(input);
            else return FALSE.onMap(input);
        });
        return this;
    }

    public <T> Mono next(Flowable<T> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public <T> Mono map(Mappable<T, Object> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public <T> Mono filter(Predicatable<T> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public Mono fork(Forkable subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public Mono join() {
        subscribers.add((Joinable) () -> true);
        return this;
    }

    @Override
    public void subscribe(Flow.Subscriber handler) {
        handler.onSubscribe(subscription.with(input)
                .setHandler(handler)
                .setSubscribers(subscribers));
    }
}