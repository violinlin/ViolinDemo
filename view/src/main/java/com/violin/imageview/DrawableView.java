package com.violin.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.violin.util.Util;

public class DrawableView extends View {
    GradientDrawable gradientDrawable = null;
    Paint paint = null;
    public DrawableView(Context context) {
        this(context,null);
    }

    public DrawableView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{Color.RED,Color.WHITE});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(Util.dp2px(getContext(),5));

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        gradientDrawable.setBounds(0,0,getWidth(),getHeight());
        gradientDrawable.draw(canvas);
//        RectF rectF = new RectF(getWidth()/2-100,0,getWidth(),getHeight());
//        canvas.drawRoundRect(rectF,getWidth()/2,getWidth()/2,paint);

    }
}
