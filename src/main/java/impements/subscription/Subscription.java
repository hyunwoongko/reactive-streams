package impements.subscription;

import impements.Executors;
import impements.protocol.Subscriber;
import impements.function.Subscribable;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-22 오후 8:56
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
public class Subscription implements impements.protocol.Subscription {

    private ConcurrentLinkedQueue<Subscribable> subscribers;
    private Subscriber handler;
    private AtomicReference<Object> input = new AtomicReference<>();

    private int methodCount = 0;
    private int completeCount = 0;
    private int errorCount = 0;

    public Subscription with(Object input) {
        this.input.set(input);
        return this;
    }

    public Subscription setHandler(Subscriber handler) {
        this.handler = handler;
        return this;
    }

    public Subscription setSubscribers(ConcurrentLinkedQueue<Subscribable> subscribers) {
        this.subscribers = subscribers;
        return this;
    }

    public synchronized void request() {
        if (input.get() instanceof Iterable) {
            for (Object once : (Iterable<?>) input.get()) {
                long numberOfInput = StreamSupport.stream(((Iterable<?>) input.get()).spliterator(), false).count();
                Object[] effectiveFinalInput = {once};
                session(numberOfInput, effectiveFinalInput);
            }
        } else {
            Object[] effectiveFinalInput = {input.get()};
            session(1, effectiveFinalInput);
        }
    }

    private void session(long numberOfInput, Object[] input) {
        boolean filter = true;
        boolean lock = false;
        final int count = 2;

        final Executor[] executor = {Executors.mainThread()};
        final CountDownLatch[] doneSignal = {new CountDownLatch(count)};

        for (Iterator<Subscribable> iterator = subscribers.iterator(); iterator.hasNext(); ) {
            Subscribable subscriber = iterator.next();
            this.methodCount++;

            try { /*FILTER 체크*/
                if (subscriber.onCheck(input[0]) != null) {
                    if (executor[0].equals(Executors.mainThread())) {
                        filter = subscriber.onCheck(input[0]);
                        continue;
                    } else throw new IllegalStateException("fork 중에는 next만 사용해주세요");
                }

                if (!filter) continue;

                if (subscriber.onJoin() != null) { /*JOIN 체크*/
                    if (!lock) lock = subscriber.onJoin();
                    else throw new IllegalStateException("불필요한 join 명령입니다.");
                    continue;
                }
                if (lock) { /*LOCK*/
                    doneSignal[0].await();
                    executor[0] = Executors.mainThread();
                }

                if (subscriber.onFork() != null) { /*FORK 체크*/
                    if (executor[0].equals(Executors.mainThread())) {
                        executor[0] = subscriber.onFork();
                        doneSignal[0] = new CountDownLatch(count);
                        lock = false;
                    } else throw new IllegalStateException("불필요한 fork 명령입니다.");
                    continue;
                }

                executor[0].execute(() -> { /*실행 세션*/
                    Object temp;
                    try {
                        temp = subscriber.onMap(input[0]);
                        if (temp == null) {
                            subscriber.onNext(input[0]);
                        } else { /* MAP연산 출력을 입력으로 ASSIGN*/
                            if (executor[0].equals(Executors.mainThread())) {
                                input[0] = temp;
                            } else throw new IllegalStateException("fork 중에는 next만 사용해주세요.");
                        }
                    } catch (Exception e) { /*ERROR 핸들링 - 멀티 쓰레드*/
                        errorCount++;
                        if (errorCount == 1) {
                            completeCount++;
                            handler.onError(e);
                            cancel();
                            return;
                        }
                    }
                    /*COMPLETE 리스너*/
                    if ((numberOfInput * subscribers.size() == methodCount && !iterator.hasNext())) {
                        completeCount++;
                        if (completeCount == 1) {
                            handler.onComplete();
                            methodCount = 0;
                        }
                    }
                    doneSignal[0].countDown();
                });
            } catch (Exception e) { /*ERROR 핸들링 - 단일 쓰레드*/
                cancel();
                handler.onError(e);
                return;
            }
            completeCount = 0;
        }
    }

    public void cancel() {
        if (!subscribers.isEmpty()) {
            subscribers.clear();
        }
    }
}