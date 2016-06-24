package com.chiemy.demo.rxandroidstudydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class Main2Activity extends AppCompatActivity {
    private int currentIndex;
    private final Long[] arr = {1000l, 3000l, 6000l, 10000l};

    private Subscription subscribtion;
    private TextView textView;
    private long tickedTime;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subscribtion == null || subscribtion.isUnsubscribed()) {
                    start();
                } else {
                    subscribtion.unsubscribe();
                }
            }
        });
        textView = (TextView) findViewById(R.id.tv);
    }

    private void start(){
        final Observable<Integer> rangeObservable = Observable.range(currentIndex, arr.length- currentIndex);
        subscribtion = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(currentIndex);
                subscriber.onCompleted();
            }
        }).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                return observable.zipWith(rangeObservable, new Func2<Void, Integer, Long>() {
                    @Override
                    public Long call(Void aVoid, Integer integer) {
                        long interval;
                        if (integer == 0) {
                            interval = arr[integer];
                        } else {
                            interval = arr[integer] - arr[integer - 1];
                        }
                        return interval - tickedTime;
                    }
                }).flatMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Long aLong) {
                        return Observable.timer(aLong, TimeUnit.MILLISECONDS)
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        tickedTime = 0;
                                        startTime = System.currentTimeMillis();
                                    }
                                })
                                .doOnNext(new Action1<Long>() {
                                    @Override
                                    public void call(Long aLong) {
                                        currentIndex++;
                                    }
                                })
                                .doOnUnsubscribe(new Action0() {
                            @Override
                            public void call() {
                                tickedTime = System.currentTimeMillis() - startTime;
                            }
                        });
                    }
                });
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        textView.setText(String.valueOf(integer));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribtion.unsubscribe();
    }
}
