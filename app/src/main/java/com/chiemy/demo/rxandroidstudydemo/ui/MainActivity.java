package com.chiemy.demo.rxandroidstudydemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chiemy.demo.rxandroidstudydemo.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView printTv;

    private int currentIndex;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printTv = (TextView) findViewById(R.id.tv_print);
        // testCreate();
        // testJust();
        // testFrom();
        // testAction();
        // printArray();
        scheduler();

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Observable.create的使用
     */
    private void testCreate() {
        Log.d(TAG, "testCreate");
        // OnSubscribe 相对于一个计划表
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("world");
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

    /**
     * Observable.just的使用
     */
    private void testJust() {
        Log.d(TAG, "testJust");
        // 和create例子等价
        Observable<String> observable = Observable.just("hello", "world");
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

    /**
     * Observable.from的使用
     */
    private void testFrom() {
        Log.d(TAG, "testFrom");
        // 和create例子等价
        String[] arr = {"hello", "world"};
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

    /**
     * ActionX的使用
     */
    private void testAction() {
        Observable observable = Observable.just("hello", "world");
        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d(TAG, "onNextAction call = " + s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.d(TAG, "onCompletedAction call");
            }
        };

        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);

    }

    /**
     * 应用举例，打印字符串
     */
    private void printArray() {
        String[] arr = {"hello", "world"};
        Observable.from(arr).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "print array = " + s);
            }
        });
    }

    /**
     * <li>Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。</li>
     * <li>Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。</li>
     * <li>Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。</li>
     * <li>Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。</li>
     * <li>另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。</li>
     */
    private void scheduler() {
        final StringBuilder builder = new StringBuilder();
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.newThread()) // 指定 subscribe() 发生在子线程中
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        builder.append(number + "\n");
                        printTv.setText(builder.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }
}
