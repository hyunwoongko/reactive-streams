package concept;

import java.util.Iterator;

public class _1_IterableImpl {
    public static void main(String[] args) {
        // for - each 구문에서 돌려면
        // Iterable 인터페이스를 구현하면 됨.

        // iterable : pull (값을 방출함)
        // observable : push (값을 밀어 넣음)

        // 아래와 같은 스타일은 미리 구현된 cold start 방식.

        Iterable<Integer> iterable = () ->
                new Iterator<>() {
                    int i = 0;
                    final static int max = 30;

                    public boolean hasNext() {
                        return i < max;
                    }

                    public Integer next() {
                        return i++;
                    }
                };

        for (Integer i : iterable) {
            System.out.println(i);
        }

        for (Iterator i = iterable.iterator(); i.hasNext(); ) {
            System.out.println(i.next());
            //데이터를 pulling 함.
        }
    }
}
