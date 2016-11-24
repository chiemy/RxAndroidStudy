package com.chiemy.demo.rxandroidstudydemo.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created: chiemy
 * Date: 16/11/24
 * Description:
 */

public class TextViewHolder extends BaseViewHolder<String> {

    public static TextViewHolder create(ViewGroup parent) {
        return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    public TextViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void render(String s) {
        ((TextView) itemView).setText(s);
    }
}
