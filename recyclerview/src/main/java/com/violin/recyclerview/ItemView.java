package com.violin.recyclerview;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by whl on 2017/8/11.
 */

public class ItemView extends LinearLayout {

    private TextView textview;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        View.inflate(context, R.layout.item_view, this);

        initView();
    }

    private void initView() {
        textview = (TextView) findViewById(R.id.textview);
    }

    public void setData(String str){
        textview.setText(str);
    }
}
