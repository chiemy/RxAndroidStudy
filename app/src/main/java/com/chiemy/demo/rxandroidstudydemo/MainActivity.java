package com.chiemy.demo.rxandroidstudydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView printTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printTv = (TextView) findViewById(R.id.tv_print);
        testCreate();
        testJust();
        testFrom();
    }

    private void testCreate(){
        Log.d(TAG, "testCreate");
        // OnSubscribe 相对于一个计划表
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("hi");
                subscriber.onCompleted();
            }
        });
        // observable被订阅时，call方法会被调用
        observable.subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext = " + s);
            }

        });
    }

    private void testJust(){
        Log.d(TAG, "testJust");
        // 和create例子等价
        Observable<String> observable = Observable.just("hello", "hi");
        observable.subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext = " + s);
            }

        });
    }

    private void testFrom(){
        Log.d(TAG, "testFrom");
        // 和create例子等价
        String [] arr = {"hello", "hi"};
        Observable<String> observable = Observable.from(arr);
        observable.subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext = " + s);
            }

        });
    }


}
