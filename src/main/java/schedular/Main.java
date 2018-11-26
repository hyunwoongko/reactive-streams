package schedular;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오후 4:26
 * @Homepage : https://github.com/gusdnd852
 */
public class Main {
    public static void main(String[] args) {
        Flow.Publisher<Integer> publisher = subscriber -> subscriber.onSubscribe(new Flow.Subscription() {
            @Override public void request(long n) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onComplete();
            }

            @Override public void cancel() {

            }
        });

        // 퍼블리셔가 느릴때 사용
        // 퍼블리셔의 행동이 다른 쓰레드에서 돌아감.
        // ex) 블락킹 IO
        Flow.Publisher<Integer> subOn = new Flow.Publisher<>() {
            @Override public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(() -> publisher.subscribe(subscriber));
            }
        };

        // 퍼블리셔가 빠를때 사용
        // 섭스크라이버의 행동이 다른 쓰레드에서 돌아감.
        // 그러나 퍼블리셔의 발행이 끝난 뒤에 실행됨.
        Flow.Publisher<Integer> punOn = new Flow.Publisher<Integer>() {
            @Override public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
                publisher.subscribe(new Flow.Subscriber<Integer>() {

                    ExecutorService service = Executors.newSingleThreadExecutor();

                    @Override public void onSubscribe(Flow.Subscription subscription) {
                        subscriber.onSubscribe(subscription);
                    }

                    @Override public void onNext(Integer item) {
                        service.execute(() ->
                                subscriber.onNext(item));
                    }

                    @Override public void onError(Throwable throwable) {
                        service.execute(() ->
                                subscriber.onError(throwable));
                    }

                    @Override public void onComplete() {
                        service.execute(subscriber::onComplete);
                    }
                });
            }
        };


        punOn.subscribe(new Flow.Subscriber<>() {
            @Override public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("SUBSCRIBE : " + Thread.currentThread().getName());
                subscription.request(Long.MAX_VALUE);
            }

            @Override public void onNext(Integer item) {
                System.out.println("NEXT : " + item + " " + Thread.currentThread().getName());
            }

            @Override public void onError(Throwable throwable) {
                System.out.println("ERROR : " + Thread.currentThread().getName());
            }

            @Override public void onComplete() {
                System.out.println("COMPLETE : " + Thread.currentThread().getName());
            }
        });


    }
}
