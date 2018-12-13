package implement;

import implement.publisher.Flux;

import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-12-14 오전 2:22
 * @Homepage : https://github.com/gusdnd852
 */
public class Test {
    public static void main(String[] args) {
        Flux.main(1, 2, 3, 4, 5)
                .filter(input -> input > 2)
                .map(input -> input * 2)
                .reduce(0, (input1, input2) -> input1 + input2)
                .subscribe(new Flow.Subscriber<>() {
                    @Override public void onSubscribe(Flow.Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }
                    @Override public void onNext(Integer item) {
                        System.out.println(item + " : " + Thread.currentThread().getName());
                    }
                    @Override public void onError(Throwable throwable) {}
                    @Override public void onComplete() {}
                });
    }
}
