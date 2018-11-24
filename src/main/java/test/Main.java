package test;

import impements.Executors;
import impements.publisher.Flux;
import impements.subscriber.With;

import java.util.Arrays;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-24 오전 6:57
 * @Homepage : https://github.com/gusdnd852
 */
public class Main {
    public static void main(String[] args) {
        With subscriber = new With()
                .complete(() -> System.out.println("컴플릿"));

        Flux.empty()
                .fork(Executors::backgroundThread)
                .next(System.out::println)
                .next(System.out::println)
                .join()
                .subscribe(subscriber);

        subscriber.onNext(Arrays.asList(1, 2, 3, 4, 5, 6));
    }
}
