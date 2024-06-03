package com.violin.violindemo;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.provider.Settings;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.violin.firstkt.FirstKT;
import com.violin.glsurfaceview.GLActivity;
import com.violin.imageview.ViewActivity;
import com.violin.service.ServiceActivity;
import com.violin.viewpager.VPActivity;
import com.violin.violindemo.palette.PaletteActivity;
import com.violin.webview.WebViewActivity;


import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private String permission;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        test();

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

        initPermission();

        getMainLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
//                Log.d(TAG, "IdleHandler queueIdle");
                return true;
            }
        });
//        getMainLooper().setMessageLogging(new Printer() {
//            @Override
//            public void println(String x) {
//                Log.d(TAG,"messageLogging:" + x);
//
//            }
//        });

        final String url = "https://violinlin.github.io/img/pictures/picture1.jpg";
        final ImageView imageView2 = findViewById(R.id.image2);
        Glide.with(this).asBitmap().load(url).into((ImageView) findViewById(R.id.image1));
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Glide.get(v.getContext()).clearDiskCache();
                    }
                }.start();
                Glide.get(v.getContext()).clearMemory();
                Glide.with(MainActivity.this).asBitmap().load((Bitmap) null).into(imageView2);
                Toast.makeText(v.getContext(), "清除缓存", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn_load_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(MainActivity.this).asBitmap().load(url).into(imageView2);

            }
        });
    }

    private void initPermission() {
        permission = Manifest.permission.READ_PHONE_STATE;
        findViewById(R.id.btn_has_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int result = MainActivity.this.checkSelfPermission(permission);
                    Toast.makeText(getApplication(), "check permission result :" + result, Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_hint_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    /**
                     * 首次安装，没有权限，shouldShow 返回false
                     * 如果已经授予权限，shouldShow 返回false
                     * 用户拒绝权限，shoudlShow 返回true
                     * 用户拒绝权限，并且勾选不再显示，返回false
                     */


                    boolean result = MainActivity.this.shouldShowRequestPermissionRationale(permission);
                    Toast.makeText(getApplication(), "shouldShow permission result :" + result, Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_request_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    MainActivity.this.requestPermissions(new String[]{permission}, 100);
                }
            }
        });

        findViewById(R.id.btn_proxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class[] cls = new Class[]{View.OnClickListener.class};
                Proxy.newProxyInstance(getClassLoader(), cls, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(proxy, args);
                    }
                });
                if (handler !=null) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);

                    Message msg1 = Message.obtain();
                    msg1.what = 2;
                    handler.sendMessageDelayed(msg1,3000);
                } else {
                    test();
                }
            }
        });
    }

    private void test() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                handler = new Handler(Looper.myLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        Log.d(TAG,"handleMessage......" + msg.what);
                    }
                };
                Looper.myLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {
                        Log.d(TAG,"queueIdle......");
                        return true;
                    }
                });
                Looper.loop();
            }
        }.start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            Toast.makeText(getApplication(), "request permission result :" + grantResults[0], Toast.LENGTH_SHORT).show();
        }
    }

    private int notifyID = 1;

    private void showNotify() {
        String notifyChannel = "test";
        NotificationManager manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {// 8.0及以上系统需要配置NotificationChannel
            //只在Android O之上需要渠道
            //channelname 用户可以在设置界面调整不同渠道的通知显示信息
            NotificationChannel notificationChannel = new NotificationChannel(notifyChannel,
                    "channelname", NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
            manager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, PaletteActivity.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification preNTF = new NotificationCompat.Builder(getApplicationContext(),
                notifyChannel)//notifyChannel 需要和 NotificationChannel中设置相同，通知才会弹出
                .setContentTitle("text:" + notifyID)
                .setContentText("message")
                .setSmallIcon(getApplicationContext().getApplicationInfo().icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.test))
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.picture1)))
                .setAutoCancel(true)
                .build();


        manager.notify(Math.min(notifyID++, 3), preNTF);//每个notifyID 对应一条通知，如果id相同，会在原来通知面板上更新内容
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

    private void okhttp() throws IOException {
        HashMap map = new HashMap();
//        map.put()
        Hashtable hashtable = new Hashtable();
//        hashtable.put();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
//        View view = new View();
//        view.post()
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        okHttpClient.newCall(new Request.Builder().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
//        okHttpClient.newCall(new Request.Builder().build()).execute()
    }

}
