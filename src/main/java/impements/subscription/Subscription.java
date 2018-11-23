package impements.subscription;

import impements.Executors;
import impements.subscriber.Subscriber;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-22 오후 8:56
 * @Homepage : https://github.com/gusdnd852
 */
@SuppressWarnings("unchecked")
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Subscription implements Flow.Subscription {

    private ConcurrentLinkedQueue<Subscriber> subscribers;
    private Flow.Subscriber handler;
    private AtomicReference<Object> input = new AtomicReference<>();

    public Subscription with(Object input) {
        this.input.set(input);
        return this;
    }

    @Override
    public synchronized void request(long backPressure) {
        if (input.get() instanceof Iterable) {
            for (Object once : (Iterable<?>) input.get()) {
                session(backPressure, once);
            }
        } else session(backPressure, input.get());
    }

    private void session(long backPressure, Object input) {
        boolean filter = true; // 디폴트 필터 : true
        boolean lock = false; // 디폴트 락 : false

        final Object[] semanticInput = {input};
        final int count = 2; // 2 -> 1 되면 await 풀리고 다음 쓰레드로
        final Executor[] executor = {Executors.mainThread()}; // 디폴트 쓰레드 = 메인쓰레드
        final CountDownLatch[] doneSignal = {new CountDownLatch(count)};
        // 이펙티브 파이널 - final 상태이지만 값을 변경할 수 있음.
        // lambda expression 내부에서 코드블럭 외부의 객체를 사용할 땐 final 키워드가 붙은 객체만 사용가능.
        for (Iterator<Subscriber> iterator = subscribers.iterator(); iterator.hasNext() && backPressure > 0; backPressure--) {
            //배압 처리 : 처리가능한 작업의 갯수를 조절해서 Subscriber 부하를 막을수 있음.
            Subscriber subscriber = iterator.next();
            try {
                if (subscriber.onCheck(semanticInput[0]) != null) {
                    if (executor[0].equals(Executors.mainThread())) {
                        filter = subscriber.onCheck(semanticInput[0]);// 필터링 여부 검사해 filter 적용.
                        continue;
                    } else throw new IllegalStateException("fork 중에는 next만 사용해주세요");
                }

                if (!filter) continue;// filter 상태가 false 이면 이번 루프를 넘김.

                if (subscriber.onJoin() != null) {// join 메서드를 받았을 경우
                    if (!lock) lock = subscriber.onJoin();// 현재 병합 여부 검사
                    else throw new IllegalStateException("불필요한 join 명령입니다.");
                    // fork 메서드는 join 상태에서만 가능함. join 연산을 두번 호출하면 엑셉션을 날림.
                    continue;
                }
                if (lock) {// 병합요청 상태
                    doneSignal[0].await();// 이번 쓰레드풀 종료까지 락을 걸어줌.
                    executor[0] = Executors.mainThread();// 락 종료이후 쓰레드를 메인쓰레드로 전환
                }
                if (subscriber.onFork() != null) {// fork 메서드를 받았을 경우
                    if (executor[0].equals(Executors.mainThread())) {// 현재 쓰레드가 메인쓰레드인지 검사
                        executor[0] = subscriber.onFork();// 포크를 요청받은 쓰레드로 전환
                        doneSignal[0] = new CountDownLatch(count);// 카운트가 1이 되었으니 새로운 카운트 생성
                        lock = false;// 병합상태를 해지함.
                    } else throw new IllegalStateException("불필요한 fork 명령입니다.");
                    // join 메서드는 fork 상태에서만 가능. fork 메서드를 두번 호출하면 엑셉션을 날림.
                    continue;
                }
                executor[0].execute(() -> {// 작업 처리 세션
                    Object temp;
                    temp = subscriber.onMap(semanticInput[0]);// 먼저 map 연산을 시도
                    if (temp == null) subscriber.onNext(semanticInput[0]);// map 연산이 아니었을경우 next 연산
                    else {
                        if (executor[0].equals(Executors.mainThread())) semanticInput[0] = temp;
                            // map 연산의 반환값을 새로운 input으로 사용함.
                        else throw new IllegalStateException("fork 중에는 next만 사용해주세요.");
                    }
                    doneSignal[0].countDown();// 카운트를 감소시켜 현재 카운트를 종료함.
                });

            } catch (Exception e) {
                cancel();
                handler.onError(e);// 에러시 onError를 호출해 에러 핸들링
                return;
            }
            if (!iterator.hasNext()) handler.onComplete();
            // 흐름 종료시 onComplete를 호출해 이벤트 종료를 notify 함
        }
    }


    @Override
    public void cancel() {
        if (!subscribers.isEmpty()) {
            subscribers.clear();
        }
    }
}
