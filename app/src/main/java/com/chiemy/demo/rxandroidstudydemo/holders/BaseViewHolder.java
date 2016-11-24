package com.chiemy.demo.rxandroidstudydemo.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    private T t;
    private OnViewHolderViewClickListener<T> listener;

    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public void onBindData(T t) {
        this.t = t;
        render(t);
    }

    public T getBindData() {
        return t;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onViewHolderViewClick(this, v, getAdapterPosition(), t, "");
        }
    }

    public void setListener(OnViewHolderViewClickListener<T> listener) {
        this.listener = listener;
    }

    protected abstract void render(T t);


    public interface OnViewHolderViewClickListener<T> {
        void onViewHolderViewClick(BaseViewHolder holder, View view, int position, T t, String tag);
    }

}
