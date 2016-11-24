package com.chiemy.demo.rxandroidstudydemo.ui.create;

import android.content.Intent;
import android.view.View;

import com.chiemy.demo.rxandroidstudydemo.holders.BaseViewHolder;
import com.chiemy.demo.rxandroidstudydemo.ui.ArrayListActivity;

import java.util.Arrays;
import java.util.List;

import eu.fiskur.markdownview.MarkdownActivity;

public class CreateOperatorActivity extends ArrayListActivity {
    private String[] createOperators = {"Create", "Defer", "Empty/Never/Throw", "From"};

    @Override
    protected List<String> getArrayList() {
        return Arrays.asList(createOperators);
    }

    @Override
    public void onViewHolderViewClick(BaseViewHolder holder, View view, int position, String s, String tag) {
        super.onViewHolderViewClick(holder, view, position, s, tag);
        int resId = getResources().getIdentifier(s.toLowerCase(), "raw", getPackageName());

        Intent intent = MarkdownActivity
                .IntentBuilder
                .getBuilder()
                .showToolbar(true)
                .title(s)
                .displayHomeAsUp(true)
                .rawFileId(resId)
                .build(CreateOperatorActivity.this);

        startActivity(intent);
    }
}
