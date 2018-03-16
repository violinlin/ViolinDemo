package com.violin.imageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_activity);
        initView();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        final ImageView imageView = findViewById(R.id.image);
        imageView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        });
        findViewById(R.id.btn_outline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(v.getContext());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setText("erewrwerewrewr");
                toast.show();
                if (imageView.getClipToOutline()) {
                    imageView.setClipToOutline(false);//关闭裁剪
                } else {
                    imageView.setClipToOutline(true);//开启裁剪
                }
            }
        });

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ImageViewActivity.class);
        context.startActivity(starter);

    }
}
