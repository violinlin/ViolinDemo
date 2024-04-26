package com.violin.activity;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.Choreographer;
import android.view.GestureDetector;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    private Choreographer.FrameCallback mFrameCallback;
    private long mPrevFrameTimeNanos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Trace.beginSection("main activity oncreate");
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity);
        initView();


        mFrameCallback = new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                long frameIntervalNanos = frameTimeNanos - mPrevFrameTimeNanos;
                double fps = 1e9 / frameIntervalNanos; // Calculate frames per second
                mPrevFrameTimeNanos = frameTimeNanos;

                // Do something with the calculated FPS, like logging or displaying on UI
                // For example:
                Log.d(TAG, "FPS: " + fps);

                // Re-register the callback for the next frame
                Choreographer.getInstance().postFrameCallback(this);
            }
        };
        Trace.endSection();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        mPrevFrameTimeNanos = System.nanoTime();
        Choreographer.getInstance().postFrameCallback(mFrameCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        Choreographer.getInstance().removeFrameCallback(mFrameCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void initView() {
        findViewById(R.id.btn_dialog_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("Test").show();
            }
        });
        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(v.getContext());
            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
}
