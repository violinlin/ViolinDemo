package com.violin.recyclerview.kit.nextpage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.violin.recyclerview.R;
import com.violin.util.Util;

/**
 * Created by wanghuilin on 2018/3/6.
 * <p>
 * email:violinlin@yeah.net
 */

public class NextPageView extends FrameLayout {

    private TextView promptTV;
    private ProgressBar progressBar;

    public NextPageView(@NonNull Context context) {
        this(context, null);
    }

    public NextPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Util.dp2px(getContext(), 50)));
        initView();
    }

    private void initView() {
        promptTV = findViewById(R.id.tv_prompt);

        progressBar = findViewById(R.id.pb_progress);
    }

    public void loading() {
        setVisibility(VISIBLE);
        promptTV.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }

    public void lastPage() {
        setVisibility(VISIBLE);
        promptTV.setVisibility(VISIBLE);
        promptTV.setText("已显示全部内容");
        progressBar.setVisibility(GONE);

    }

    public void error(final Listener listener) {
        setVisibility(VISIBLE);
        promptTV.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        promptTV.setText("加载错误，请点击重试");
        if (listener != null) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRetryListener();
                    setOnClickListener(null);
                }
            });
        }
    }

    public void hide() {
        setVisibility(GONE);
    }

    public interface Listener {
        void onRetryListener();
    }

}
