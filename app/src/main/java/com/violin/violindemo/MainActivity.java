package com.violin.violindemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.violin.glsurfaceview.GLActivity;
import com.violin.imageview.ImageViewActivity;
import com.violin.recyclerview.ReclyclerViewActivity;
import com.violin.viewpager.VPActivity;
import com.violin.violindemo.coco.CCActivity;
import com.violin.violindemo.coco.SDKWrapper;

import org.cocos2dx.lib.Cocos2dxActivity;

import java.util.List;

public class MainActivity extends Cocos2dxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {

        Button activityBtn = findViewById(R.id.btn_activity);
        activityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                com.violin.activity.MainActivity.start(v.getContext());
                Intent intent = new Intent("com.violin.action");
                intent.addCategory("com.violin.category");

                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (resolveInfos != null && resolveInfos.size() > 0) {
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), "未匹配到合适Activity", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button recyclerBtn = findViewById(R.id.btn_recyclerview);
        recyclerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReclyclerViewActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_glsurfaceview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GLActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_vp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VPActivity.start(v.getContext());
            }
        });
        findViewById(R.id.btn_coco).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CCActivity.start(v.getContext());
//                MainActivity.this.finish();
            }
        });
    }
}
