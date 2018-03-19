package com.violin.glsurfaceview;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl);
        initView();
    }

    private void initView() {
        GLSurfaceView surfaceView=findViewById(R.id.surfaceview);

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, GLActivity.class);
        context.startActivity(starter);
    }
}
