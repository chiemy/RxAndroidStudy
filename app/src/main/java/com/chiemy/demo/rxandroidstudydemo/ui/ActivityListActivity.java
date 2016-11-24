package com.chiemy.demo.rxandroidstudydemo.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;

import com.chiemy.demo.rxandroidstudydemo.Utils;
import com.chiemy.demo.rxandroidstudydemo.adapter.ActivityListAdapter;
import com.chiemy.demo.rxandroidstudydemo.holders.BaseViewHolder;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public class ActivityListActivity extends RecyclerViewActivity<ActivityInfo> {
    protected static final String EXTRA_FILTER_TAG = "filter_tag";

    protected ActivityListAdapter adapter;

    @Override
    protected void initData() {
        Utils
                .getActivities(this, getFilterTag())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ActivityInfo>>() {
                    @Override
                    public void call(List<ActivityInfo> infoList) {
                        adapter.setData(infoList);
                    }
                });
    }

    @Override
    protected ActivityListAdapter getAdapter() {
        return adapter = new ActivityListAdapter();
    }

    protected String getFilterTag() {
        return getIntent().getStringExtra(EXTRA_FILTER_TAG);
    }

    @Override
    public void onViewHolderViewClick(BaseViewHolder holder, View view, int position, ActivityInfo info, String tag) {
        try {
            Intent intent = new Intent(ActivityListActivity.this, Class.forName(info.name));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
