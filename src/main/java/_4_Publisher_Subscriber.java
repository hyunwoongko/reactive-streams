import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
// 자바9부터 기본 패키지가 됨.

public class _4_Publisher_Subscriber {
    public static void main(String[] args) {
        // Publisher <-- Observable
        // Subscriber <-- Observer

        // Reactive Streams 표준 :
        // response to call a Publisher.subscribe(Subscriber)
        // 필수 : onSubscribe
        // 옵션 : onNext x n / (onError|onComplete) x 1

        // Publisher -----> Subscriber -----backPressure----> subscription
        // subscription <-----> Publisher


        Iterable<Integer> iterator = Arrays.asList(1, 2, 3, 4, 5);


        Publisher<Integer> publisher = new Publisher<Integer>() {
            @Override public void subscribe(Subscriber<? super Integer> subscriber) {
                Iterator<Integer> it = iterator.iterator();

                Flow.Subscription subscription = new Flow.Subscription() {
                    @Override public void request(long n) {
                        while (n-- > 0) {
                            // back pressure(배압) 를 구현하기 위한 메소드임
                            // 일정 갯수만 데이터를 보냄으로써 subscriber의 부하를 막음
                            if (it.hasNext()) subscriber.onNext(it.next());
                            else {
                                subscriber.onComplete();
                                break;
                            }
                        }
                    }

                    @Override public void cancel() {

                    }
                };


                // 누가 이 데이터를 받는지 구독자를 알아야한다.
                subscriber.onSubscribe(subscription);
            }
        };


        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            Flow.Subscription subscription;
            @Override public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("subs : " + subscription);
                this.subscription = subscription;
                subscription.request(1);

            }

            @Override public void onNext(Integer item) {
                System.out.println("item : " + item);
                subscription.request(1);
            }

            @Override public void onError(Throwable throwable) {
                System.out.println("error : " + throwable);
            }

            @Override public void onComplete() {
                System.out.println("complete");
            }
        };

        publisher.subscribe(subscriber);
    }
}