package com.violin.imageview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BatteryView extends AppCompatImageView {

    static Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Bitmap mBitmap;

    int mBitmapWidth = 0;
    int mBitmapHeight = 0;
    private int left;

    private static int top;

    private static float percent;

    private static float right;
    private static float bottom;

    private static boolean isChecked = false;

    private Resources resources;

    public BatteryView(Context context) {
        super(context);

        resources = context.getResources();
        getRect();
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        resources = context.getResources();
        getRect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int color_int = Color.BLACK;
        mPaint.setColor(color_int);
        canvas.drawRect(left, getPaddingTop() + top + 3, (right - left) * percent + (left - 2),
                getPaddingTop() + bottom + 2, mPaint);
    }

    private void getRect() {
        if (!isChecked) {
            isChecked = true;
//            mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
            mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.reader_batty_icon);
            setImageBitmap(mBitmap);
            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
            for (int i = 0; i < mBitmapWidth; i++) {
                int color = mBitmap.getPixel(i, mBitmapHeight / 2);
                if (color == 0) {
                    this.left = i;
                    break;
                }
            }
            for (int i = mBitmapWidth - 1; i >= 0; i--) {
                int color = mBitmap.getPixel(i, mBitmapHeight / 2);
                if (color == 0) {
                    this.right = i;
                    break;
                }
            }
            for (int i = 0; i < mBitmapHeight; i++) {
                int color = mBitmap.getPixel(mBitmapWidth / 2, i);
                if (color == 0) {
                    this.top = i;
                    break;
                }
            }
            for (int i = mBitmapHeight - 1; i >= 0; i--) {
                int color = mBitmap.getPixel(mBitmapWidth / 2, i);
                if (color == 0) {
                    this.bottom = i;
                    break;
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        synchronized (BatteryView.class) {
            if (mBatInfoReceiver == null) {
                mBatInfoReceiver = new BatteryReceiver();
                try {
                   getContext().registerReceiver(
                            mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mBatInfoReceiver.listeners.add(BatteryView.this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            getContext().unregisterReceiver(mBatInfoReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void clean() {
        synchronized (BatteryView.class) {
            if (mBatInfoReceiver != null) {
                mBatInfoReceiver.listeners.clear();
                try {
                   getContext().unregisterReceiver(
                            mBatInfoReceiver);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mBatInfoReceiver = null;
                }
            }
        }
    }

    public static void update() {
        if (mBatInfoReceiver != null && mBatInfoReceiver.listeners != null) {
            for (BatteryView batteryView : mBatInfoReceiver.listeners) {
                batteryView.postInvalidate();
            }
        }
    }

    /**
     * 接受电量改变广播
     */
    private static volatile BatteryReceiver mBatInfoReceiver = null;

    public static class BatteryReceiver extends BroadcastReceiver {
        public static Set<BatteryView> listeners = Collections.synchronizedSet(
                new HashSet<BatteryView>());

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Intent.ACTION_BATTERY_CHANGED) {
                float level = intent.getIntExtra("level", 0);
                float scale = intent.getIntExtra("scale", 100);
                percent = level / scale;
                for (BatteryView batteryView : listeners) {
                    batteryView.postInvalidate();
                    batteryView.destroyDrawingCache();
                }
            }
        }
    }
}