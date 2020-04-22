package com.violin.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initView();
    }

    private MyService.MyBindler mProgressBindler;

    private IRemoteService mStub;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("whl", "onServiceConnected");
            mProgressBindler = (MyService.MyBindler) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("whl", "onServiceDisconnected");

        }
    };

    private ServiceConnection mAIDLConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mStub =  IRemoteService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mStub = null;
        }
    };

    private void initView() {
        final Button startService = findViewById(R.id.btn_start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyService.class);
                startService(intent);

            }
        });
        findViewById(R.id.btn_stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyService.class);
                stopService(intent);
            }
        });


        Button bindService = findViewById(R.id.btn_bind_service);
        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyService.class);
//                startService(intent);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.btn_unbind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mServiceConnection);
            }
        });

        findViewById(R.id.btn_bind_AIDL_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AIDLService.class);
                bindService(intent, mAIDLConnection, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.btn_unbind_AIDL_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mAIDLConnection);
            }
        });

        findViewById(R.id.btn_getAIDL_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStub!=null){
                    try {
                        Log.d("whl","AIDL-Pid"+mStub.getPid());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ServiceActivity.class);
        context.startActivity(starter);
    }
}
