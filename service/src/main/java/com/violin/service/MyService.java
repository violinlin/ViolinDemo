package com.violin.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private static final String TAG = MyService.class.getSimpleName();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        showNotify();

    }

    private void showNotify() {
        String notifyChannel = "service";
        NotificationManager manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        /**
         * 8.0及以上系统需要配置NotificationChannel
         * 如果没配置，{@link #startForeground(int, Notification)}
         * 会抛出 invalid channel for service notification 异常
         *
         */
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //channelname 用户可以在设置界面调整不同渠道的通知显示信息
            NotificationChannel notificationChannel = new NotificationChannel(notifyChannel,
                    "service", NotificationManager.IMPORTANCE_HIGH);

            manager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, ServiceActivity.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification preNTF = new NotificationCompat.Builder(getApplicationContext(),
                notifyChannel)//notifyChannel 需要和 NotificationChannel中设置相同，通知才会弹出
                .setContentTitle("service")
                .setContentText("message")
                .setSmallIcon(getApplicationContext().getApplicationInfo().icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.test1))
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.picture1)))
                .setAutoCancel(true)
                .build();


        startForeground(1000, preNTF);//每个notifyID 对应一条通知，如果id相同，会在原来通知面板上更新内容
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        Toast.makeText(getApplicationContext(), "Service#OnStartCommand", Toast.LENGTH_SHORT).show();

        getHandler().removeCallbacksAndMessages(null);
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "延迟Toast", Toast.LENGTH_SHORT).show();

            }
        }, 3000);


        return super.onStartCommand(intent, flags, startId);
    }

    private Handler handler;

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        MyBindler myBindler = new MyBindler();
        return myBindler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    class MyBindler extends Binder {
        public void init() {
            Log.d(TAG, "MyBindler init()");
        }

        public void play() {

        }

        public int getProgress() {
            return 0;
        }

        public void stop() {
            Log.d(TAG, "MyBindler stop()");
        }
    }
}
