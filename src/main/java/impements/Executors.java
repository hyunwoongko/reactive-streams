package impements;

import java.util.concurrent.Executor;

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
