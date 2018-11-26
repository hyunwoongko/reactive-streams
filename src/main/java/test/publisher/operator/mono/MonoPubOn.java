package test.publisher.operator.mono;

import test.publisher.Mono;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오후 6:02
 * @Homepage : https://github.com/gusdnd852
 */
public class MonoPubOn<T> extends Mono<T> {

    private final Mono<T> mono;

    public MonoPubOn(Mono<T> mono) {
        this.mono = mono;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
    }
}
