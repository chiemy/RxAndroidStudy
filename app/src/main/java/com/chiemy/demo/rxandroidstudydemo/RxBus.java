package com.chiemy.demo.rxandroidstudydemo;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created: chiemy
 * Date: 16/6/17
 * Description:
 */
public class RxBus {

    private static volatile RxBus mDefaultInstance;

    private RxBus() {
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    private final Subject<Object, Object> rxBus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        rxBus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return rxBus;
    }
}
