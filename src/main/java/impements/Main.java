package impements;

import impements.publisher.Mono;
import impements.subscriber.Empty;

import java.util.concurrent.TimeUnit;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-24 오후 3:12
 * @Homepage : https://github.com/gusdnd852
 */
public class Main {
    public static void main(String[] args) {
        Empty subscriber = Empty.create();

        Mono.with("HELLO")
                .fork(Executors::backgroundThread)
                .next(input -> { try { TimeUnit.SECONDS.sleep(2);System.out.println(input + " : " + Thread.currentThread().getName()); } catch (InterruptedException e) { e.printStackTrace(); } })
                .next(input -> { try { TimeUnit.SECONDS.sleep(2);System.out.println(input + " : " + Thread.currentThread().getName()); } catch (InterruptedException e) { e.printStackTrace(); } })
                .next(input -> { try { TimeUnit.SECONDS.sleep(2);System.out.println(input + " : " + Thread.currentThread().getName()); } catch (InterruptedException e) { e.printStackTrace(); } })
                .next(input -> { try { TimeUnit.SECONDS.sleep(2);System.out.println(input + " : " + Thread.currentThread().getName()); } catch (InterruptedException e) { e.printStackTrace(); } })
                .next(input -> { try { TimeUnit.SECONDS.sleep(2);System.out.println(input + " : " + Thread.currentThread().getName()); } catch (InterruptedException e) { e.printStackTrace(); } })
                .next(input -> { try { TimeUnit.SECONDS.sleep(2);System.out.println(input + " : " + Thread.currentThread().getName()); } catch (InterruptedException e) { e.printStackTrace(); } })
                .join()
                .next(System.out::println)
                .next(System.out::println)
                .subscribe(subscriber);

        subscriber.onNext(null);
    }
}
