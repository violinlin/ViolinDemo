package com.violin.violindemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.violin.firstkt.FirstKT;
import com.violin.glsurfaceview.GLActivity;
import com.violin.imageview.ViewActivity;
import com.violin.service.ServiceActivity;
import com.violin.viewpager.VPActivity;
import com.violin.violindemo.palette.PaletteActivity;
import com.violin.webview.WebViewActivity;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();

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
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
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
//                ReclyclerViewActivity.start(v.getContext());
                JumpUtils.recyclerView(v.getContext());
            }
        });

        findViewById(R.id.btn_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewActivity.start(v.getContext());
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

        findViewById(R.id.btn_service).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ServiceActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_webview).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                WebViewActivity.start(v.getContext());

            }
        });

        findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_kotlin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstKT firstKT = new FirstKT();
                firstKT.start(v.getContext());
            }
        });
        findViewById(R.id.btn_rxtest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxTest();
            }
        });

        findViewById(R.id.btn_palette).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaletteActivity.Companion.start(v.getContext());
            }
        });

        findViewById(R.id.btn_notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotify();
            }
        });
    }

    private void showNotify() {
        NotificationManager nftmgr = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel("channelid1","channelname",NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
            nftmgr.createNotificationChannel(notificationChannel);
        }
        Notification preNTF = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(getApplicationContext().getApplicationInfo().icon)
                .setContentTitle("text")
                .setContentText("message").build();
        preNTF.when = System.currentTimeMillis();
        preNTF.flags = Notification.FLAG_AUTO_CANCEL;

        nftmgr.notify(1,preNTF);
    }


    private void rxTest() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(Integer.parseInt("1q"));
                emitter.onComplete();

            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext:" + integer + "\n" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError:" + e.getMessage() + "\n"
                                + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:" + "\n" + Thread.currentThread().getName());

                    }
                });


    }
}
