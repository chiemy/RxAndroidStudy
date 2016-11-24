package com.chiemy.demo.rxandroidstudydemo.adapter;

import android.content.pm.ActivityInfo;
import android.view.ViewGroup;

import com.chiemy.demo.rxandroidstudydemo.holders.ActivityInfoViewHolder;
import com.chiemy.demo.rxandroidstudydemo.holders.BaseViewHolder;

/**
 * Created: chiemy
 * Date: 16/11/23
 * Description:
 */

public class ActivityListAdapter extends BaseAdapter<ActivityInfo> {

    @Override
    public BaseViewHolder<ActivityInfo> getViewHolder(ViewGroup parent, int viewType) {
        return ActivityInfoViewHolder.create(parent);
    }

}
