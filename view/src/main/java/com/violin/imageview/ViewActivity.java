package com.violin.imageview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.violin.imageview.view.FlowLayout;
import com.violin.imageview.view.ShapeTextView;

/**
 * Created by wanghuilin on 2018/5/7.
 * <p>
 * email:wanghuilin@zshiliu.com
 */

public class ViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context,
                                     AttributeSet attrs) {

                if (name.equals("Button")) {

                    Button button = new Button(context, attrs);
                    button.setTypeface(
                            Typeface.createFromAsset(getAssets(), "ZCOOLKuaiLe-Regular.ttf"));
                    return button;
                }

                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                Log.d("whl  222", "" + name + context.toString());
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout);
        initView();
    }

    private void initView() {
        Button roundView = findViewById(R.id.btn_round);
        roundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_constraintlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_typeface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeFaceActivity.Companion.start(v.getContext());
            }
        });

        FlowLayout flowLayout = findViewById(R.id.flowlayout);

        for (int i = 0; i < 1; i++) {
            flowLayout.addView(new ShapeTextView(this));
        }

        new AsyncTask<String, String, Integer>() {

            @Override
            protected Integer doInBackground(String... strings) {
                return null;
            }
        }.execute("");

        findViewById(R.id.btn_view2bitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveViewActivity.Companion.start(v.getContext());
            }
        });
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, ViewActivity.class);
        context.startActivity(starter);
    }

}
