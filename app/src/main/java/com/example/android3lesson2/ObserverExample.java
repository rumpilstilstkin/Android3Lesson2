package com.example.android3lesson2;

import android.util.Log;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class ObserverExample {

    public void example() {

        Observable<String> o = Observable.just("Hello");

        DisposableObserver<String> observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
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
        o.subscribe(observer);

        observer.dispose();

        // subscribe with

        Disposable d = o.subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
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
        });

        d.dispose();

        Flowable<String> f = Flowable.just("Hello");
        Disposable fd = f.subscribeWith(new DisposableSubscriber<String>() {
            @Override
            public void onNext(String s) {
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
        });

        Maybe<String> m = Maybe.just("Hello");
        Disposable md = m.subscribeWith(new DisposableMaybeObserver<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("Dto", "onSuccess");
            }

            @Override
            public void onComplete() {
                Log.d("Dto", "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Dto", "onError");
            }
        });

        Single<String> s = Single.just("Hello");
        Disposable sd = s.subscribeWith(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("Dto", "onSuccess");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Dto", "onError");
            }
        });

        Completable c = Completable.complete();
        Disposable cd = c.subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Log.d("Dto", "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Dto", "onError");
            }
        });

        //составная подписка

        CompositeDisposable disposables = new CompositeDisposable();

        disposables.add(o.subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
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
        }));

        disposables.add(o.subscribeWith(new DisposableObserver<String>(){
            @Override
            public void onNext(String s) {
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
        }));

        disposables.dispose();

    }
}
