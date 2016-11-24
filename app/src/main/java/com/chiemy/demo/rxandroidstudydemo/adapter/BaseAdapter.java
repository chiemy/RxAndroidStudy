package com.chiemy.demo.rxandroidstudydemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chiemy.demo.rxandroidstudydemo.holders.BaseViewHolder;

import java.util.List;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> implements BaseViewHolder.OnViewHolderViewClickListener<T> {
    private List<T> data;
    public BaseAdapter() {
    }

    public void setData(List<T> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder vh = getViewHolder(parent, viewType);
        vh.setListener(this);
        return vh;
    }

    public abstract BaseViewHolder<T> getViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        holder.onBindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public void onViewHolderViewClick(BaseViewHolder holder, View view, int position, T t, String tag) {
        if (listener != null) {
            listener.onViewHolderViewClick(holder, view, position, t, tag);
        }
    }

    protected BaseViewHolder.OnViewHolderViewClickListener<T> listener;
    public void setOnItemClickListener(BaseViewHolder.OnViewHolderViewClickListener<T> listener) {
        this.listener = listener;
    }
}
