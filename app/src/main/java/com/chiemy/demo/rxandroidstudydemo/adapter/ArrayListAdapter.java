package com.chiemy.demo.rxandroidstudydemo.adapter;

import android.view.ViewGroup;

import com.chiemy.demo.rxandroidstudydemo.holders.BaseViewHolder;
import com.chiemy.demo.rxandroidstudydemo.holders.TextViewHolder;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public class ArrayListAdapter extends BaseAdapter<String> {

    @Override
    public BaseViewHolder<String> getViewHolder(ViewGroup parent, int viewType) {
        return TextViewHolder.create(parent);
    }
}
