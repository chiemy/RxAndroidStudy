package com.chiemy.demo.rxandroidstudydemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chiemy.demo.rxandroidstudydemo.R;
import com.chiemy.demo.rxandroidstudydemo.TimeNodeSchedule;

import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private int currentIndex;

    private TextView textView;
    private final Long[] timeNodes = {1000l, 3000l, 6000l, 10000l};
    private TimeNodeSchedule timeNodeSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timeNodeSchedule.isRunning()) {
                    timeNodeSchedule.startSchedule();
                } else {
                    timeNodeSchedule.pause();
                }
            }
        });
        textView = (TextView) findViewById(R.id.tv);
        List<Long> timeNodeList = Arrays.asList(timeNodes);
        timeNodeSchedule = new TimeNodeSchedule(timeNodeList);
        timeNodeSchedule.setTimeNodeScheduleCallback(new TimeNodeSchedule.TimeNodeScheduleCallback() {
            @Override
            public void onTimeNode(int index, long time) {
                textView.setText(String.valueOf(index));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeNodeSchedule.stop();
    }
}
