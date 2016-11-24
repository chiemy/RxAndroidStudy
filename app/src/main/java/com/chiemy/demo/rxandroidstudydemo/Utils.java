package com.chiemy.demo.rxandroidstudydemo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created: chiemy
 * Date: 16/11/23
 * Description:
 */

public class Utils {
    public static Observable<List<ActivityInfo>> getActivities(final Context context, final String label) {
        final Observable<ActivityInfo> observable =
                Observable
                        .just(context)
                        .flatMap(new Func1<Context, Observable<ActivityInfo>>() {
                            @Override
                            public Observable<ActivityInfo> call(Context context) {
                                PackageManager pm = context.getPackageManager();

                                PackageInfo info = null;
                                try {
                                    info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                                return Observable.from(info.activities);
                            }
                        })
                        .filter(new Func1<ActivityInfo, Boolean>() {
                            @Override
                            public Boolean call(ActivityInfo activityInfo) {
                                String theLabel = null;
                                if (activityInfo.labelRes > 0) {
                                    theLabel = getStringResourceName(context, activityInfo.labelRes);
                                }
                                return TextUtils.isEmpty(label) || (!TextUtils.isEmpty(theLabel) && theLabel.startsWith(label));
                            }
                        });

        return Observable.create(new Observable.OnSubscribe<List<ActivityInfo>>() {
            @Override
            public void call(final Subscriber<? super List<ActivityInfo>> subscriber) {
                final List<ActivityInfo> infos = new ArrayList<>(5);
                observable.subscribe(new Subscriber<ActivityInfo>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onNext(infos);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onNext(ActivityInfo activityInfo) {
                        infos.add(activityInfo);
                    }
                });
            }
        });
    }

    private static String getStringResourceName(Context context, int resId) {
        String fullName = context.getResources().getResourceName(resId);
        return TextUtils.isEmpty(fullName) ? "" : fullName.replace(context.getPackageName() + ":string/", "");
    }
}
