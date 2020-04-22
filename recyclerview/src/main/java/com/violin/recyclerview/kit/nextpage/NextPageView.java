package com.violin.recyclerview.kit.nextpage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.violin.recyclerview.R;
import com.violin.util.Util;

/**
 * Created by wanghuilin on 2018/3/6.
 * <p>
 * email:violinlin@yeah.net
 */

public class NextPageView extends LinearLayout {

    private TextView promptTV;
    private ProgressBar progressBar;

    public NextPageView(@NonNull Context context) {
        this(context, null);
    }

    public NextPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, Util.dp2px(getContext(), 50));
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(params);
        View.inflate(context, R.layout.kit_nextpage_view, this);
        initView();
    }

    private void initView() {
        promptTV = findViewById(R.id.tv_prompt);

        progressBar = findViewById(R.id.pb_progress);
    }


    public void loading(String prompt) {
        setVisibility(VISIBLE);
        promptTV.setText(prompt);
        progressBar.setVisibility(VISIBLE);
    }

    public void lastPage(String prompt) {
        setVisibility(VISIBLE);
        promptTV.setVisibility(VISIBLE);
        promptTV.setText(prompt);
        progressBar.setVisibility(GONE);

    }

    public void error(String prompt, final Listener listener) {
        setVisibility(VISIBLE);
        promptTV.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        promptTV.setText(prompt);
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
