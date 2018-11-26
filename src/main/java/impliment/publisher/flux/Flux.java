package impliment.publisher.flux;

import impliment.publisher.flux.operater.FluxFilter;
import impliment.publisher.flux.operater.FluxMapper;
import impliment.publisher.flux.operater.FluxNexter;
import impliment.publisher.flux.operater.FluxReducer;
import impliment.publisher.flux.subscription.EmitSubscription;
import impliment.publisher.flux.subscription.WithSubscription;

import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오전 7:20
 * @Homepage : https://github.com/gusdnd852
 */
public class Flux<T> implements Flow.Publisher<T> {

    protected Flux() {
    } // don't use constructor !

    @SafeVarargs
    public static <T> Flux<T> with(T... inputs) {
        return new Flux<>() {
            @Override
            public void subscribe(Flow.Subscriber<? super T> subscriber) {
                subscriber.onSubscribe(new WithSubscription<>(subscriber, Arrays.asList(inputs)));
            }
        };
    }

    public static <T> Flux<T> emit(Consumer<Flow.Subscriber<? super T>> consumer) {
        return new Flux<>() {
            @Override
            public void subscribe(Flow.Subscriber<? super T> emitter) {
                emitter.onSubscribe(new EmitSubscription<>(emitter, consumer));
            }
        };
    }

    public <R> Flux<R> map(Function<T, R> function) {
        return new FluxMapper<>(this, function);
    }

    public <R> Flux<R> reduce(R init, BiFunction<R, T, R> biFunction) {
        return new FluxReducer<>(this, init, biFunction);
    }

    public Flux<T> filter(Predicate<T> predicate) {
        return new FluxFilter<>(this, predicate);
    }

    public Flux<T> next(Consumer<T> consumer) {
        return new FluxNexter<>(this, consumer);
    }

    @Override public void subscribe(Flow.Subscriber<? super T> subscriber) {
    }
}