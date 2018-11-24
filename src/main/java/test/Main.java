package test;

import impements.Executors;
import impements.publisher.Flux;
import impements.publisher.Mono;
import impements.subscriber.Empty;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-24 오전 6:57
 * @Homepage : https://github.com/gusdnd852
 */
public class Main {

    static class Module {
        Empty subscriber = Empty.create()
                .complete(() -> System.out.println("흐름 종료"))
                .error(Throwable::printStackTrace);

        Flux flux = Flux.with(1)
                .<Integer>map(input -> input * 2)
                .next(input -> System.out.println("출력 : " + input));

        public Module() {
            flux.subscribe(subscriber);
        }

        public void flow() {
            subscriber.onNext(Long.MAX_VALUE);
        }
    }

    public static void main(String[] args) {
        Module module = new Module();
        module.flow();
        module.flow();
    }
}
