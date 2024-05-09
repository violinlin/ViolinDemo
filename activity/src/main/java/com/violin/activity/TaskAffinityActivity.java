package com.violin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TaskAffinityActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("TaskAffinityActivity");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
        setContentView(textView);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, TaskAffinityActivity.class);
        context.startActivity(starter);
    }
}
