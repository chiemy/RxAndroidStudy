package com.chiemy.demo.rxandroidstudydemo.ui;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

public class Main3Activity extends ActivityListActivity {

    @Override
    protected String getFilterTag() {
        String filterTag = null;
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            filterTag = info.metaData.getString(EXTRA_FILTER_TAG);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return filterTag;
    }
}
