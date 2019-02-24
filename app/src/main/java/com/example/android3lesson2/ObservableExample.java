package com.example.android3lesson2;

import android.annotation.SuppressLint;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Action;

public class ObservableExample {

    @SuppressLint("CheckResult")
    private void example() {

        //just

        Flowable.just("Hello");
        Flowable.just("Hello", "World");

        Observable.just("Hello");
        Observable.just("Hello", "World");

        Maybe.just(324.23423);
        Single.just("Hello");

        // array or list

        String[] array = { "Hello", "World" };
        List<String> list = Arrays.asList(array);

        Flowable.fromArray(array);
        Flowable.fromIterable(list);

        Observable.fromArray(array);
        Observable.fromIterable(list);

        //callable

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                //TODO
                return "Hello";
            }
        };


        Flowable.fromCallable(callable);

        Observable.fromCallable(callable);

        Maybe.fromCallable(callable);

        Single.fromCallable(callable);

        Completable.fromCallable(callable);

        //interval

        Observable.interval(200, TimeUnit.MILLISECONDS);

        Flowable.interval(200, TimeUnit.MILLISECONDS);
        
        // just for maybe and completable

        Action action = new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("Hello");
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };

        Maybe.fromAction(action);
        Maybe.fromRunnable(runnable);

        Completable.fromAction(action);
        Completable.fromRunnable(runnable);

        //create

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello");
                e.onNext("fhgf");
                e.onError(new Exception());
                //e.onComplete();
            }
        });

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) {
                e.onNext("Hello");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) {
                emitter.onSuccess("Hello");
            }
        });

        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                emitter.onComplete();
            }
        });

        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) {
                emitter.onSuccess("Hello");
            }
        });
    }
}
