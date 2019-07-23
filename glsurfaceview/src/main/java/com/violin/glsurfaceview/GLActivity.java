package com.violin.glsurfaceview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class GLActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, GLActivity.class);
        context.startActivity(starter);
    }
}
