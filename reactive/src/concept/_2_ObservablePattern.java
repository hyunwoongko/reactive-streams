package concept;

import java.util.Observable;
import java.util.Observer;

// 자바9부터 depreciate 됨.
@SuppressWarnings("deprecation")
public class _2_ObservablePattern {


    static class IntObservable extends Observable implements Runnable {

        @Override public void run() {
            for (int i = 1; i < 10; i++) {
                setChanged();
                notifyObservers(i);        //  <-- push
                // int i = iterable.next()     <-- pull
            }
        }
    }

    public static void main(String[] args) {
        /**@see Observable : 이벤트 혹은 데이터 소스*/
        /**@see Observer : Observable을 관찰함*/

        Observer observer = (o, arg) -> System.out.println(arg);
        IntObservable intObservable = new IntObservable();

        intObservable.addObserver(observer);
        intObservable.run();
        // 실행과 선언의 분리
        // 옵저버블이 옵저버에게 이벤트를 push
    }
}
