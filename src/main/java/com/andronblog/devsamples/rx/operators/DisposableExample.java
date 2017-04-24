package com.andronblog.devsamples.rx.operators;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


import java.util.concurrent.Callable;

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples/blob/master/app/src/main/java/com/rxjava2/android/samples/ui/operators/DisposableExampleActivity.java
public class DisposableExample {

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public static void main(String[] args) {
        new DisposableExample();
    }

    public DisposableExample() {

        Observable<String> observable = Observable
                .defer(mWorker)
                .subscribeOn(Schedulers.io()) // Schedulers.single()
                .observeOn(Schedulers.io());  // Schedulers.single()

        Disposable disposable = observable.subscribeWith(mCallback);
        mDisposables.add(disposable);

        DevUtils.println("|wait before");
        for (int i = 0; i < 5; i++) {
            try {
                //mDisposables.clear();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DevUtils.println("|wait after");

    }

    private Callable<ObservableSource<? extends String>> mWorker = new Callable<ObservableSource<? extends String>>() {

        @Override
        public ObservableSource<? extends String> call() throws Exception {
            DevUtils.println("|call is started");
            Thread.sleep(2000);
            DevUtils.println("|call is stopped");
            return Observable.just("one", "two", "three", "four", "five");
        }
    };

    private DisposableObserver<String> mCallback = new DisposableObserver<String>() {

        @Override
        public void onNext(String s) {
            DevUtils.println("|next: " + s);
        }

        @Override
        public void onError(Throwable e) {
            DevUtils.println("|error");
        }

        @Override
        public void onComplete() {
            DevUtils.println("|onComplete");
        }
    };

    private static String getTid() {
        return String.valueOf(Thread.currentThread().getId());
    }
}
