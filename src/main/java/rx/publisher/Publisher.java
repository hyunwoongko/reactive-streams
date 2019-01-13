package rx.publisher;


import rx.operator.*;
import rx.operator.Error;
import rx.subscription.Subscription;
import utils.Pool;
import utils.function.BiFunction;
import utils.function.Consumer;
import utils.function.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.Predicate;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 7:20
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Publisher<T> implements Flow.Publisher<T> {

    @SafeVarargs
    public static <T> Publisher<T> main(T... inputs) {
        List<T> inputAsList = new ArrayList<>();
        for (T input : inputs) {
            if (input instanceof Iterable)
                for (T once : (Iterable<T>) input)
                    inputAsList.add(once);
            else inputAsList.add(input);
        }
        return new Publisher<>() {
            @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
                Pool.main().execute(() -> subscriber.onSubscribe(new Subscription<>(subscriber, inputAsList)));
            }
        };
    }

    @SafeVarargs
    public static <T> Publisher<T> background(T... inputs) {
        List<T> inputAsList = new ArrayList<>();
        for (T input : inputs) {
            if (input instanceof Iterable)
                for (T once : (Iterable<T>) input)
                    inputAsList.add(once);
            else inputAsList.add(input);
        }
        return new Publisher<>() {
            @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
                Pool.background().execute(() -> subscriber.onSubscribe(new Subscription<>(subscriber, inputAsList)));
            }
        };
    }

    protected Publisher() {
    }

    public <R> Publisher<R> map(Function<T, R> function) {
        return new Mapper<>(this, function);
    }

    public <R> Publisher<R> reduce(R init, BiFunction<R, T, R> biFunction) {
        return new Reducer<>(this, init, biFunction);
    }

    public Publisher<T> filter(Predicate<T> predicate) {
        return new Filter<>(this, predicate);
    }

    public Publisher<T> next(Consumer<T> consumer) {
        return new Proceeder<>(this, consumer);
    }

    public <R> Publisher<Publisher<R>> flatMap(Function<T, Publisher<R>> flatMapper) {
        return new Flatter<>(this, flatMapper);
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
    }

    public Publisher<T> error(Consumer<Throwable> consumer) {
        return new Error<>(this, consumer);
    }

    public Publisher<T> complete(Runnable runnable) {
        return new Complete<>(this, runnable);
    }
}