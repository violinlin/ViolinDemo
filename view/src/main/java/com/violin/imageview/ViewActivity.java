package com.violin.imageview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wanghuilin on 2018/5/7.
 * <p>
 * email:wanghuilin@zshiliu.com
 */

public class ViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ViewActivity.class);
        context.startActivity(starter);
    }

}
