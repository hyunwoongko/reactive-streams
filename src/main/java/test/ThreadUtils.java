package test;

import operator.Publisher;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-26 오후 9:16
 * @Homepage : https://github.com/gusdnd852
 */
public class ThreadUtils {

    private static final ExecutorService service = Executors.newCachedThreadPool();

    public static ExecutorService background() {
        return service;
    }

    public static CountDownLatch countDown(int count) {
        return new CountDownLatch(count);
    }

    public static String name(){
        return Thread.currentThread().getName();
    }
}
