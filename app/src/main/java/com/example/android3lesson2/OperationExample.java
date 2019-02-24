package com.example.android3lesson2;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class OperationExample {

    public static void example1() {
        DisposableObserver<Integer> observer = new DisposableObserver<Integer>() {

            @Override
            public void onNext(Integer s) {
                Log.d("Dto", "onNext" + s);
            }

            @Override
            public void onComplete() {
                Log.d("Dto", "onComplete");
            }

            @Override
            public void onError(Throwable t) {
                Log.d("Dto", "onError");
            }
        };

        DisposableObserver<Integer> d =
                Observable.just("2", "13", "y43", "5", "y43", "1", "7", "30", "3", "1")
                .distinct()
                .filter(new Predicate<String>() {

                    @Override
                    public boolean test(String s) throws Exception {
                        return s.contains("3");
                    }
                })
                .map(new Function<String, Integer>() {

                         @Override
                         public Integer apply(String s) {
                             Log.d("Dto", "funk");
                             return Integer.parseInt(s);
                         }
                     }
                )
                .doOnError(new Consumer<Throwable>() {

                                @Override
                                public void accept(Throwable throwable) {
                                    Log.d("Dto", "onError do work");
                                }
                            }

        )
                .onErrorResumeNext(Observable.just(2, 4, 5))

                .map(new Function<Integer, Integer>() {

                    @Override
                    public Integer apply(Integer integer) {
                        Log.d("Dto", "map in io");
                        return -1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
