package impements.processor;

import impements.publisher.Publisher;
import impements.subscriber.Flowable;
import impements.subscriber.Forkable;
import impements.subscriber.Mappable;
import impements.subscriber.Predicatable;

import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * 단일입력 처리가 가능한 프로세서로
 * 한개의 데이터를 발행하고 스스로 구독을 할 수 있음.
 */
@SuppressWarnings("unchecked")
public class Mono extends Publisher implements Flow.Processor {

    private Flow.Subscription subscription;
    private Consumer<Throwable> errorHandler;
    private Runnable completeHandler;


    public <T> Mono(T input) {
        this.input = input;
    }

    public static <T> Mono with(T input) {
        return new Mono(input);
    }

    public <T> Mono switchNext(Predicatable<T> predicatable,
                               Flowable<T> TRUE,
                               Flowable<T> FALSE) {
        super.switchNext(predicatable, TRUE, FALSE);
        return this;
    }

    public <T> Mono switchMap(Predicatable<T> predicatable,
                              Mappable<T, Object> TRUE,
                              Mappable<T, Object> FALSE) {
        super.switchMap(predicatable, TRUE, FALSE);
        return this;
    }

    public <T> Mono next(Flowable<T> subscriber) {
        super.next(subscriber);
        return this;
    }

    public <T> Mono map(Mappable<T, Object> subscriber) {
        super.map(subscriber);
        return this;
    }


    public <T> Mono filter(Predicatable<T> subscriber) {
        super.filter(subscriber);
        return this;
    }

    public Mono fork(Forkable subscriber) {
        super.fork(subscriber);
        return this;
    }

    public Mono join() {
        super.join();
        return this;
    }

    public Mono error(Consumer<Throwable> errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    public Mono complete(Runnable completeHandler) {
        this.completeHandler = completeHandler;
        return this;
    }

    public void subscribe() {
        super.subscribe(this);
    }

    @Override
    public void onError(Throwable throwable) {
        if (errorHandler != null)
            this.errorHandler.accept(throwable);
    }

    @Override
    public void onComplete() {
        if (completeHandler != null)
            this.completeHandler.run();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(Object input) {
        subscription.request(Long.MAX_VALUE);
    }
}