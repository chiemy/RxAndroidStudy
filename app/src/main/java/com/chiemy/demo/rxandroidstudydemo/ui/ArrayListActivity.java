package com.chiemy.demo.rxandroidstudydemo.ui;

import android.view.View;

import com.chiemy.demo.rxandroidstudydemo.adapter.ArrayListAdapter;
import com.chiemy.demo.rxandroidstudydemo.holders.BaseViewHolder;

import java.util.List;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public abstract class ArrayListActivity extends RecyclerViewActivity<String> {
    private ArrayListAdapter adapter;

    @Override
    protected ArrayListAdapter getAdapter() {
        return adapter = new ArrayListAdapter();
    }

    @Override
    protected void initData() {
        adapter.setData(getArrayList());
    }

    @Override
    public void onViewHolderViewClick(BaseViewHolder holder, View view, int position, String s, String tag) {
    }

    protected abstract List<String> getArrayList();
}
