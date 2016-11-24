package com.chiemy.demo.rxandroidstudydemo.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chiemy.demo.rxandroidstudydemo.R;
import com.chiemy.demo.rxandroidstudydemo.adapter.BaseAdapter;
import com.chiemy.demo.rxandroidstudydemo.holders.BaseViewHolder;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public abstract class RecyclerViewActivity<T> extends AppCompatActivity implements BaseViewHolder.OnViewHolderViewClickListener<T> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();
        initData();
    }

    @LayoutRes
    protected int getLayoutId(){
        return R.layout.activity_recyclerview;
    }

    protected void initView() {
        RecyclerView recyclerView = getRecyclerView();
        recyclerView.setLayoutManager(getLayoutManager());
        BaseAdapter<T> adapter = getAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    protected RecyclerView getRecyclerView() {
        return (RecyclerView) findViewById(R.id.recycler_view);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected abstract BaseAdapter<T> getAdapter();

    protected abstract void initData();
}
