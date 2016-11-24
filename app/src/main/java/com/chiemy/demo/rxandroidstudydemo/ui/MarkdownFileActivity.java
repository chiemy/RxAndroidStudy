package com.chiemy.demo.rxandroidstudydemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chiemy.demo.rxandroidstudydemo.R;

import eu.fiskur.markdownview.MarkdownView;

public class MarkdownFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_file);

        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdown_view);
        // markdownView.showMarkdown();
    }
}
