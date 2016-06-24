package com.chiemy.demo.rxandroidstudydemo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created: chiemy
 * Date: 16/6/24
 * Description:
 */
public class TimeNodeSchedule {
    private List<Long> timeNodes;

    private int currentIndex;

    private Subscription subscribtion;
    private long tickedTime;
    private long startTime;
    private boolean finished;
    private boolean running;

    private TimeNodeScheduleCallback timeNodeScheduleCallback;

    public TimeNodeSchedule(List<Long> timeNodes) {
        this.timeNodes = timeNodes;
    }

    public void setTimeNodeScheduleCallback(TimeNodeScheduleCallback timeNodeScheduleCallback) {
        this.timeNodeScheduleCallback = timeNodeScheduleCallback;
    }

    public void startSchedule() {
        if (running) {
            return;
        }
        running = true;
        subscribtion = scheduleTimeNode(timeNodes)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        finished = true;
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        running = false;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (timeNodeScheduleCallback != null) {
                            timeNodeScheduleCallback.onTimeNode(integer, timeNodes.get(integer));
                        }
                    }
                });
    }


    public void pause() {
        if (subscribtion != null && !subscribtion.isUnsubscribed() && !finished) {
            subscribtion.unsubscribe();
        }
    }

    public void resume() {
        if (subscribtion != null && (subscribtion.isUnsubscribed() || finished)) {
            startSchedule();
        }
    }

    public void stop() {
        if (subscribtion != null) {
            subscribtion.unsubscribe();
            subscribtion = null;
        }
    }

    public void reset() {
        stop();
        currentIndex = 0;
        tickedTime = 0;
        startTime = 0;
        running = false;
        finished = false;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isFinished() {
        return finished;
    }

    private Observable<Integer> scheduleTimeNode(final List<Long> timeNodes) {
        return Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(currentIndex);
                        subscriber.onCompleted();
                    }
                })
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return rangeDelay(timeNodes, observable);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable rangeDelay(final List<Long> timeNodes, Observable<? extends Void> observable) {

        return Observable
                // Observable.range(开始数字, 次数);
                .range(currentIndex, timeNodes.size() - currentIndex - 1)
                // 合并. Func2<调用zipWith的Observab的参数类型, 被合并的Obserbable参数, 合并后的输出>
                .zipWith(observable, new Func2<Integer, Void, Long>() {
                    @Override
                    public Long call(Integer integer, Void aVoid) {
                        long interval;
                        if (integer == 0) {
                            interval = timeNodes.get(integer);
                        } else {
                            interval = timeNodes.get(integer) - timeNodes.get(integer - 1);
                        }
                        return interval - tickedTime;
                    }
                })
                .flatMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(Long aLong) {
                        return delay(aLong);
                    }
                });
    }

    private Observable<Long> delay(long delay) {
        return Observable.timer(delay, TimeUnit.MILLISECONDS)
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

    public interface TimeNodeScheduleCallback {
        void onTimeNode(int index, long time);
    }
}
