package impements;

import java.util.concurrent.Executor;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-22 오후 8:56
 * @Homepage : https://github.com/gusdnd852
 */
public class Executors {
    private static Executor backgroundThread = java.util.concurrent.Executors.newCachedThreadPool();
    private static Executor mainThread = Runnable::run;

    public static Executor backgroundThread() {
        return backgroundThread;
    }

    public static Executor mainThread() {
        return mainThread;
    }
}
