package com.violin.imageview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class LineView extends ViewGroup {

    private Paint paint;

    public LineView(Context context) {
        super(context);
        init();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(5); // 设置线的宽度
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 在这里设置子视图的位置
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(0, 0, r - l, b - t);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // 画一条线，从左上角到右下角
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
    }
}

