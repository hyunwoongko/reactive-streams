package utils;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author : Hyunwoong
 * @When : 2019-01-14 오전 12:29
 * @Homepage : https://github.com/gusdnd852
 */
public class Pool {
    private static final Executor mainThread = Runnable::run;
    private static final List<Executor> backgroundThread = new Vector<>();
    private static final Random random = new Random();

    public static Executor background() {
        if (backgroundThread.isEmpty()) for (int i = 0; i < 16; i++)
            backgroundThread.add(Executors.newSingleThreadExecutor());
        return backgroundThread.get(random.nextInt(15));
    }

    public static Executor main() {
        return mainThread;
    }
}
