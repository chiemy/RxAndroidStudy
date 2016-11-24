package com.chiemy.demo.rxandroidstudydemo.holders;

import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public class ActivityInfoViewHolder extends BaseViewHolder<ActivityInfo> {

    public static ActivityInfoViewHolder create(ViewGroup parent) {
        return new ActivityInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    private ActivityInfoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void render(ActivityInfo info) {
        ((TextView) itemView).setText(info.labelRes);
    }
}
