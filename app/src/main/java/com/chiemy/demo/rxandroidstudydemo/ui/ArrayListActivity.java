package com.chiemy.demo.rxandroidstudydemo.ui;

import com.chiemy.demo.rxandroidstudydemo.adapter.ArrayListAdapter;

import java.util.List;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public abstract class ArrayListActivity extends RecyclerViewActivity {
    private ArrayListAdapter adapter;

    @Override
    protected ArrayListAdapter getAdapter() {
        return adapter = new ArrayListAdapter();
    }

    @Override
    protected void initData() {
        adapter.setData(getArrayList());
    }

    protected abstract List<String> getArrayList();
}
