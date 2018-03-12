package com.violin.imageview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_activity);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ImageViewActivity.class);
        context.startActivity(starter);
    }
}
