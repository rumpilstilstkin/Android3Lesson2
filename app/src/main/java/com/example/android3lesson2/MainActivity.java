package com.example.android3lesson2;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OperationExample.example1();
    }

    private void example() {
        Observer<String> obser = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("Dto", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d("Dto", "onNext = " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Dto", "onError = " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("Dto", "onComplete ");
            }
        };
        String[] array = { "Hello", "World" };
        List<String> list = Arrays.asList(array);

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                Log.d("Dto", "some work");
                return "Hello";
            }
        };

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello");
                e.onNext("fhgf");
               // e.onError(new Exception());
                e.onComplete();
            }
        }).subscribe(obser);

        //obs.subscribe(obser);
    }


    // следим за отпиской
    @SuppressLint("CheckResult")
    private void cancel(){
        final View v = new View(this);

        Observable.create(new ObservableOnSubscribe<View>() {
            @Override
            public void subscribe(final ObservableEmitter<View> e) {
                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        v.setOnClickListener(null);
                    }
                });
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        e.onNext(v);
                    }
                });
            }
        });
    }

    private void backpressure() {
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .doOnNext(new Consumer<Long>() {

                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("Dto2", "accept " + aLong);
                    }
                })
                //.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        //nope
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Dto", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Dto", "onError " + e);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        try {
                            TimeUnit.SECONDS.sleep(30);
                            Log.d("Dto", "onNext " + aLong);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
