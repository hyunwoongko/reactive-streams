package test.publisher;

import test.publisher.operator.flux.FluxPubOn;
import test.publisher.operator.flux.FluxSubOn;
import test.publisher.operator.mono.*;
import test.subscription.EmitSubscription;
import test.subscription.MonoSubscription;

import java.util.concurrent.Flow;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오후 3:26
 * @Homepage : https://github.com/gusdnd852
 */
public class Mono<T> implements Flow.Publisher<T> {

    protected Mono() {
    } // don't use constructor !

    public static <T> Mono<T> with(T input) {
        return new Mono<>() {
            @Override
            public void subscribe(Flow.Subscriber<? super T> subscriber) {
                subscriber.onSubscribe(new MonoSubscription<>(subscriber, input));
            }
        };
    }

    public static <T> Mono<T> emit(Consumer<Flow.Subscriber<? super T>> consumer) {
        return new Mono<>() {
            @Override
            public void subscribe(Flow.Subscriber<? super T> emitter) {
                emitter.onSubscribe(new EmitSubscription<>(emitter, consumer));
            }
        };
    }

    public <R> Mono<R> map(Function<T, R> function) {
        return new MonoMapper<>(this, function);
    }

    public <R> Mono<R> reduce(R init, BiFunction<R, T, R> biFunction) {
        return new MonoReducer<>(this, init, biFunction);
    }

    public Mono<T> filter(Predicate<T> predicate) {
        return new MonoFilter<>(this, predicate);
    }

    public Mono<T> next(Consumer<T> consumer) {
        return new MonoNexter<>(this, consumer);
    }


    public Mono<T> subscribeOn() {
        return new MonoSubOn<>(this);
    }

    public Mono<T> publishOn() {
        return new MonoPubOn<>(this);
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {

    }
}
