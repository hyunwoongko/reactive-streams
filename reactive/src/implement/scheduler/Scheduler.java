package implement.scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-30 오후 1:29
 * @Homepage : https://github.com/gusdnd852
 */
public class Scheduler {
    private static final Executor singleBackgroundThreadPool = Executors.newSingleThreadExecutor();
    private static final Executor mainThread = Runnable::run;

    public static Executor background() {
        return singleBackgroundThreadPool;
    }

    public static Executor main() {
        return mainThread;
    }
}
