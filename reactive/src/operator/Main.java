package operator;

import java.util.concurrent.Flow;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-24 오전 6:57
 * @Homepage : https://github.com/gusdnd852
 */
public class Main {
    public static void main(String[] args) {

        Iterable iterable = Stream.iterate(1, integer -> integer + 1)
                .limit(10)
                .collect(Collectors.toList());

        Flow.Subscriber subscriber = new Subscriber();
        Flow.Publisher<Integer> publisher = new Publisher(iterable);
        Flow.Publisher<Integer> mapPublisher = new Mapper(publisher, integer -> integer * 10);
        Flow.Publisher<Integer> redPublisher = new Reducer<>(mapPublisher, 0, (integer, integer2) -> integer+integer2);
        redPublisher.subscribe(subscriber);


    }
}
