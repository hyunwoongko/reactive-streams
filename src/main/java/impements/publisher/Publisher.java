package impements.publisher;

import impements.subscriber.*;
import impements.subscription.Subscription;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Flow;


@SuppressWarnings("unchecked")
public class Publisher implements Flow.Publisher {

    private ConcurrentLinkedQueue<Subscriber> subscribers = new ConcurrentLinkedQueue<>();
    protected Object input;

    public <T> Publisher switchNext(Predicatable<T> predicatable,
                                    Flowable<T> TRUE,
                                    Flowable<T> FALSE) {
        subscribers.add((Flowable<T>) input -> {
            if (predicatable.onCheck(input)) TRUE.onNext(input);
            else FALSE.onNext(input);
        });
        return this;
    }

    public <T> Publisher switchMap(Predicatable<T> predicatable,
                                   Mappable<T, Object> TRUE,
                                   Mappable<T, Object> FALSE) {
        subscribers.add((Mappable<T, Object>) input -> {
            if (predicatable.onCheck(input)) return TRUE.onMap(input);
            else return FALSE.onMap(input);
        });
        return this;
    }

    public <T> Publisher next(Flowable<T> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public <T> Publisher map(Mappable<T, Object> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public <T> Publisher filter(Predicatable<T> subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public Publisher fork(Forkable subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    public Publisher join() {
        subscribers.add((Joinable) () -> true);
        return this;
    }

    @Override
    public void subscribe(Flow.Subscriber handler) {
        handler.onSubscribe(new Subscription(subscribers, handler).with(input));
    }
}