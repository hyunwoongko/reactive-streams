package test.publisher.operator.flux;

import test.publisher.Flux;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오후 6:02
 * @Homepage : https://github.com/gusdnd852
 */
public class FluxSubOn<T> extends Flux<T> {

    private final Flux<T> flux;

    public FluxSubOn(Flux<T> flux) {
        this.flux = flux;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
    }
}
