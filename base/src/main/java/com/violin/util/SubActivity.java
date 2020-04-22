package com.violin.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_FRAGMENT = "fragment";
    private ViewStub mTitleLayout;
    private ImageView mRightButton;

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_sub);
        mTitleLayout = (ViewStub) findViewById(R.id.layout_title);


        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }

        String fragmentName = getIntent().getStringExtra(EXTRA_FRAGMENT);
        mFragment = getFragment(fragmentName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, mFragment);
        transaction.commit();
    }

    protected Fragment getFragment(String name) {
        Bundle args = getIntent().getExtras();
        mFragment = Fragment.instantiate( this, name, args);
        return mFragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    private void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleLayout.setVisibility(View.VISIBLE);
            TextView tv_title = (TextView) findViewById(R.id.tv_title);
            ImageView iv_back = (ImageView) findViewById(R.id.btn_left);
            mRightButton = (ImageView) findViewById(R.id.btn_right);

            tv_title.setText(title);

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void finish() {
        if (mFragment instanceof SubListener) {// 判断页面是否可以结束
            if (!((SubListener) mFragment).canFinish()) {
                return;
            }
        }
        super.finish();
    }

    public interface SubListener {
        boolean canFinish();
    }
}
