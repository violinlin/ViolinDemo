package com.violin.glsurfaceview;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;


public class GLActivity extends Activity {
    GLSurfaceView glSurfaceView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl);
        glSurfaceView = findViewById(R.id.surfaceview);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setFocusableInTouchMode(true);


        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glSurfaceView.setRenderer(new GlRender(glSurfaceView));
        glSurfaceView.setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
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
