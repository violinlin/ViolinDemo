package com.violin.webview.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.violin.webview.R;

import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Created by wanghuilin on 2017/12/11.
 * <p>
 * email:violinlin@yeah.net
 */

public class ExtendWebView extends FrameLayout {
    private WebView webView;

    private ProgressBar progressBar;

    private Listener mListener;

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public ExtendWebView(@NonNull Context context) {
        this(context, null);
    }

    public ExtendWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.wb_widget_web_layout, this);
        initView();

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_view);
        initWebSetting(webView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    /**
     * WebSettings的设置
     */
    private void initWebSetting(WebView webView) {

        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);

            }
        };

        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                if (mListener != null) {
                    mListener.onPageStart(url);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 加载https的不安全网站处理 例如https://www.12306.cn/mormhweb/
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                if (mListener != null) {
                    mListener.onPageFinish(url);
                }

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (mListener != null) {
                    mListener.onPageError();
                }
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {

//                boolean isOverRide = super.shouldOverrideUrlLoading(webView, s);
                Log.d("whl", "url " + s);
                Log.d("whl", "isoverride  " + URLDecoder.decode(s));
//                return super.shouldOverrideUrlLoading(webView, s);
                if (s.startsWith("http://") || s.startsWith("https://")) {
                    webView.loadUrl(s);
//                   return super.shouldOverrideUrlLoading(webView,s);
                } else {
                    try {
                        Uri uri = Uri.parse(s);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        getContext().startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                return true;

            }
        };
        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//缓存模式，不加载缓存


        webSettings.setJavaScriptEnabled(true);
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);

        // 开启 AppCache API功能
//        webView.getSettings().setAppCacheMaxSize(1024*1024*8);
//        String appCachePath = getContext().getApplicationContext().getCacheDir().getAbsolutePath();
//        webView.getSettings().setAppCachePath(appCachePath);
//        webView.getSettings().setAppCacheEnabled(true);
//
//        webView.getSettings().setAllowFileAccess(true);

        //允许混合内容 解决部分手机 加载不出https请求里面的http下的图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Log.d("whl", "down:  " + url);
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    getContext().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
//        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片,默认为true
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            webView.setWebContentsDebuggingEnabled(true);
////        }
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
//        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

    }

    public WebView getWebView() {
        return webView;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearWebView();

    }

    private void clearWebView() {
        if (webView != null) {
//            webView.stopLoading();
//            webView.clearHistory();
//            webView.clearCache(true);
            webView.loadUrl("about:blank");
            webView.removeAllViews();
            webView.destroy();
        }
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onPageStart(String url);

        void onPageFinish(String url);

        void onPageError();
    }
}
