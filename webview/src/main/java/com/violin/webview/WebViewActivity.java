package com.violin.webview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.violin.webview.webview.ExtendWebView;

public class WebViewActivity extends AppCompatActivity {

    private ExtendWebView mExtendWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
    }

    private void initView() {
        mExtendWebView = findViewById(R.id.ex_webview);
//        mExtendWebView.getWebView().loadUrl("https://www.baidu.com/");
//        extendWebView.getWebView().loadUrl("https://www.jianshu.com/");
//        extendWebView.getWebView().loadUrl("https://blog.androidhuilin.wang/");
        mExtendWebView.getWebView().loadUrl("http://192.168.4.36:8080/cspShare/html/share.html");
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, WebViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {
        if (mExtendWebView.getWebView().canGoBack()){
            mExtendWebView.getWebView().goBack();
        }else {
            super.onBackPressed();
        }

    }
}
