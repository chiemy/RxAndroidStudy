package com.chiemy.demo.rxandroidstudydemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chiemy.demo.rxandroidstudydemo.R;


public class MarkdownFileActivity extends AppCompatActivity {
    private static final String EXTRA_LABEL = "label";

    public static void start(Context context, String label) {
        Intent intent = new Intent(context, MarkdownFileActivity.class);
        intent.putExtra(EXTRA_LABEL, label);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_file);
        setTitle(getIntent().getStringExtra(EXTRA_LABEL));
    }
}
