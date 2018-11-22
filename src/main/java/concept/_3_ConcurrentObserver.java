package concept;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 자바9부터 depreciate 됨.
@SuppressWarnings("deprecation")
public class _3_ConcurrentObserver {

    static class IntObservable extends Observable implements Runnable {

        @Override public void run() {
            for (int i = 1; i < 10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }

    public static void main(String[] args) {
        Observer observer = (o, arg) -> System.out.println(arg + Thread.currentThread().getName());
        IntObservable observable = new IntObservable();

        observable.addObserver(observer);
        // observable.run();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(observable); // <-- 나중

        System.out.println(Thread.currentThread().getName() + "EXIT");
        service.shutdown(); // <-- 먼저

        // PULL 보다 PUSH 가 concurrent 환경에서 더 유리해
        // 그러나 문제..
        // 1. when is complete ??
        // 2. how can we handle the exception (error) ??

    }
}
