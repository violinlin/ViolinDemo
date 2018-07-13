package com.violin.violindemo;

import android.app.Application;
import android.util.Log;

/**
 * Date: 2018/7/5 14:13
 * Author: wanghuilin
 * Mail: huilin_wang@dingyuegroup.cn
 * Desc:
 */

public class MyApplication extends Application{

    private static String TAG=MyApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");

    }
}
