package com.violin.imageview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.violin.imageview.R;


public class AspectRatioFrameLayout extends FrameLayout {

    public String aspectRatio = null;

    public AspectRatioFrameLayout(@NonNull Context context) {
        super(context);
    }

    public AspectRatioFrameLayout(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AspectRatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;
    float radius;

    private Paint roundPaint;
    private Paint imagePaint;

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                    R.styleable.AspectRatioFrameLayout);

            aspectRatio = typedArray.getString(R.styleable.AspectRatioFrameLayout_aspect_ratio);
            radius = typedArray.getDimension(R.styleable.AspectRatioFrameLayout_aspect_radius, 0f);
            if (radius > 0){
                topLeftRadius = topRightRadius = bottomRightRadius = bottomLeftRadius = radius;
            } else {
                topLeftRadius =typedArray.getDimension(R.styleable.AspectRatioFrameLayout_aspect_left_top_radius,0f);
                topRightRadius = typedArray.getDimension(R.styleable.AspectRatioFrameLayout_aspect_right_top_radius,0f);
                bottomLeftRadius = typedArray.getDimension(R.styleable.AspectRatioFrameLayout_aspect_left_bottom_radius,0f);
                bottomRightRadius = typedArray.getDimension(R.styleable.AspectRatioFrameLayout_aspect_right_bottom_radius,0f);
            }
            typedArray.recycle();
        }

        roundPaint = new Paint();
        roundPaint.setColor(Color.WHITE);
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        imagePaint = new Paint();
        imagePaint.setXfermode(null);
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (aspectRatio != null && aspectRatio.split("_").length == 2) {
//            int scaleHeight = SspSdkUtils.getScaleHeight(MeasureSpec.getSize(widthMeasureSpec),
//                    aspectRatio);
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(scaleHeight,MeasureSpec.EXACTLY
//                    );
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (topRightRadius + topLeftRadius + bottomRightRadius + bottomLeftRadius == 0) {
            super.dispatchDraw(canvas);
        } else {
            canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), imagePaint,
                    Canvas.ALL_SAVE_FLAG);
            super.dispatchDraw(canvas);
            drawTopLeft(canvas);
            drawTopRight(canvas);
            drawBottomLeft(canvas);
            drawBottomRight(canvas);
            canvas.restore();
        }

    }

    private void drawTopLeft(Canvas canvas) {
        if (topLeftRadius > 0) {
            Path path = new Path();
            path.moveTo(0, topLeftRadius);
            path.lineTo(0, 0);
            path.lineTo(topLeftRadius, 0);
            path.arcTo(new RectF(0, 0, topLeftRadius * 2, topLeftRadius * 2),
                    -90, -90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawTopRight(Canvas canvas) {
        if (topRightRadius > 0) {
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - topRightRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, topRightRadius);
            path.arcTo(new RectF(width - 2 * topRightRadius, 0, width,
                    topRightRadius * 2), 0, -90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomLeft(Canvas canvas) {
        if (bottomLeftRadius > 0) {
            int height = getHeight();
            Path path = new Path();
            path.moveTo(0, height - bottomLeftRadius);
            path.lineTo(0, height);
            path.lineTo(bottomLeftRadius, height);
            path.arcTo(new RectF(0, height - 2 * bottomLeftRadius,
                    bottomLeftRadius * 2, height), 90, 90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomRight(Canvas canvas) {
        if (bottomRightRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - bottomRightRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - bottomRightRadius);
            path.arcTo(new RectF(width - 2 * bottomRightRadius, height - 2
                    * bottomRightRadius, width, height), 0, 90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }
}
