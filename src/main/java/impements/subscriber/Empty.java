package impements.subscriber;

import impements.protocol.Subscriber;
import impements.subscription.Subscription;

import java.util.function.Consumer;

/**
 * @Author : Hyunwoong
 * @When : 2018-11-24 오전 5:37
 * @Homepage : https://github.com/gusdnd852
 */
public class Empty implements Subscriber<Void> {

    private Subscription subscription;
    private Consumer<Throwable> errorHandler;
    private Runnable completeListener;

    public static Empty create() {
        return new Empty();
    }

    public Empty error(Consumer<Throwable> errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    public Empty complete(Runnable completeListener) {
        this.completeListener = completeListener;
        return this;
    }

    @Override public void onError(Throwable throwable) {
        if (errorHandler != null) errorHandler.accept(throwable);
        else throwable.printStackTrace();
    }

    @Override public void onComplete() {
        if (completeListener != null) completeListener.run();
    }

    @Override public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override public void onNext(Void nullInput) {
        subscription.request();
    }
}
