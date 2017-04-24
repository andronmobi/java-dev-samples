package com.andronblog.devsamples.rx.operators;


import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples/blob/master/app/src/main/java/com/rxjava2/android/samples/ui/operators/FlowableExampleActivity.java
public class FlowableExample {

    public static void main(String[] args) {
        new FlowableExample();
    }

    public FlowableExample() {

        // We will accumulate the result of arguments multiplication and return it
        // i.e. 50 * 1 = 50; 50 * 2 = 100; 100 * 3 = 300; 300 * 4 = 1200;
        Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4);

        Single<Integer> single = flowable.reduce(50, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer i1, Integer i2) throws Exception {
                return i1 * i2;
            }
        });

//        single.subscribe(mObserver); // synchronous execution in the main thread
         single.subscribeOn(Schedulers.io()).subscribe(mObserver); // async execution in a different thread

        DevUtils.println("FlowableExample wait before ");
        // wait the result
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DevUtils.println("FlowableExample wait after");
    }

    SingleObserver<Integer> mObserver = new SingleObserver<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            DevUtils.println("onSubscribe disposed: " + d.isDisposed());
        }

        @Override
        public void onSuccess(Integer res) {
            // Here we return the result of multiplication
            DevUtils.println("onSuccess result : " + res);

        }

        @Override
        public void onError(Throwable e) {
            DevUtils.println("onError: " + e.getMessage());
        }
    };
}
