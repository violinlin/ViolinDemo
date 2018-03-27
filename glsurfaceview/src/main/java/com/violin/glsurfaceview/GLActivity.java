package com.violin.glsurfaceview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class GLActivity extends Activity {
    private CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);
        director = CCDirector.sharedDirector();
        director.attachInView(surfaceView);//开线程
        director.setScreenSize(720, 1080);
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);//竖屏
        director.setDisplayFPS(true);//不显示帧率
        CCScene scene = CCScene.node();
        scene.addChild(new SplashLayer());
        //导演管理场景
        director.runWithScene(scene);
    }

    @Override
    protected void onResume() {
        director.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        director.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        director.end();
        super.onDestroy();
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, GLActivity.class);
        context.startActivity(starter);
    }
}
