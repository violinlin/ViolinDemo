package com.violin.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.violin.util.Util;

/**
 * Created by wanghuilin on 2018/3/12.
 * <p>
 * email:violinlin@yeah.net
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_CIRCLE_BACKGROUND_COLOR = Color.TRANSPARENT;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;
    private static final boolean DEFAULT_ISOVAL = false;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();
    private final Paint mCircleBackgroundPaint = new Paint();

    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    private int mCircleBackgroundColor = DEFAULT_CIRCLE_BACKGROUND_COLOR;

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mRadius;

    private ColorFilter mColorFilter;

    private boolean mReady;
    private boolean mSetupPending;
    private boolean mBorderOverlay;
    private boolean mDisableCircularTransformation;
    private boolean mIsOval;

    public RoundImageView(Context context) {
        super(context);

        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyle, 0);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.RoundImageView_round_border_width, DEFAULT_BORDER_WIDTH);

        mBorderColor = a.getColor(R.styleable.RoundImageView_round_border_color, DEFAULT_BORDER_COLOR);

        mBorderOverlay = a.getBoolean(R.styleable.RoundImageView_round_border_overlay, DEFAULT_BORDER_OVERLAY);

        mRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_round_corner_radius, 0);

        mIsOval = a.getBoolean(R.styleable.RoundImageView_round_oval, DEFAULT_ISOVAL);

        a.recycle();

        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new OutlineProvider());
        }

        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDisableCircularTransformation) {
            super.onDraw(canvas);
            return;
        }

        if (mBitmap == null) {
            return;
        }
        if (mIsOval) {
            // 绘制圆形
            if (mCircleBackgroundColor != Color.TRANSPARENT) {
                canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRect.width() / 2f, mCircleBackgroundPaint);
            }
            canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRect.width() / 2f, mBitmapPaint);
            if (mBorderWidth > 0) {
//                绘制圆形的半径等于圆心到画笔宽度中心的距离
                canvas.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRect.width() / 2f - mBorderWidth / 2f, mBorderPaint);
            }

        } else {
            // 绘制圆角矩形
            if (mCircleBackgroundColor != Color.TRANSPARENT) {
                canvas.drawRoundRect(mDrawableRect, mRadius, mRadius, mCircleBackgroundPaint);
            }
            canvas.drawRoundRect(mDrawableRect, mRadius, mRadius, mBitmapPaint);
            if (mBorderWidth > 0) {
                RectF rectF = new RectF(mBorderRect);
                //                绘制圆角矩形的宽/高等于矩形中心到上/下画笔宽度中心的距离
                rectF.inset(mBorderWidth / 2f, mBorderWidth / 2f);
                canvas.drawRoundRect(rectF, mRadius, mRadius, mBorderPaint);
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        setup();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        setup();
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        if (borderColor == mBorderColor) {
            return;
        }

        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

//    /**
//     * @deprecated Use {@link #setBorderColor(int)} instead
//     */
//    @Deprecated
//    public void setBorderColorResource(@ColorRes int borderColorRes) {
//        setBorderColor(getContext().getResources().getColor(borderColorRes));
//    }

    public int getCircleBackgroundColor() {
        return mCircleBackgroundColor;
    }

    public void setCircleBackgroundColor(@ColorInt int circleBackgroundColor) {
        if (circleBackgroundColor == mCircleBackgroundColor) {
            return;
        }

        mCircleBackgroundColor = circleBackgroundColor;
        mCircleBackgroundPaint.setColor(circleBackgroundColor);
        invalidate();
    }

    public void setCircleBackgroundColorResource(@ColorRes int circleBackgroundRes) {
        setCircleBackgroundColor(getContext().getResources().getColor(circleBackgroundRes));
    }

//    /**
//     * Return the color drawn behind the circle-shaped drawable.
//     *
//     * @return The color drawn behind the drawable
//     * @deprecated Use {@link #getCircleBackgroundColor()} instead.
//     */
//    @Deprecated
//    public int getFillColor() {
//        return getCircleBackgroundColor();
//    }

//    /**
//     * Set a color to be drawn behind the circle-shaped drawable. Note that
//     * this has no effect if the drawable is opaque or no drawable is set.
//     *
//     * @param fillColor The color to be drawn behind the drawable
//     * @deprecated Use {@link #setCircleBackgroundColor(int)} instead.
//     */
//    @Deprecated
//    public void setFillColor(@ColorInt int fillColor) {
//        setCircleBackgroundColor(fillColor);
//    }
//
//    /**
//     * Set a color to be drawn behind the circle-shaped drawable. Note that
//     * this has no effect if the drawable is opaque or no drawable is set.
//     *
//     * @param fillColorRes The color resource to be resolved to a color and
//     *                     drawn behind the drawable
//     * @deprecated Use {@link #setCircleBackgroundColorResource(int)} instead.
//     */
//    @Deprecated
//    public void setFillColorResource(@ColorRes int fillColorRes) {
//        setCircleBackgroundColorResource(fillColorRes);
//    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if (borderWidth == mBorderWidth) {
            return;
        }

        mBorderWidth = borderWidth;
        setup();
    }

    public boolean isBorderOverlay() {
        return mBorderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == mBorderOverlay) {
            return;
        }

        mBorderOverlay = borderOverlay;
        setup();
    }

    public boolean isDisableCircularTransformation() {
        return mDisableCircularTransformation;
    }

    public void setDisableCircularTransformation(boolean disableCircularTransformation) {
        if (mDisableCircularTransformation == disableCircularTransformation) {
            return;
        }

        mDisableCircularTransformation = disableCircularTransformation;
        initializeBitmap();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) {
            return;
        }

        mColorFilter = cf;
        applyColorFilter();
        invalidate();
    }

    @Override
    public ColorFilter getColorFilter() {
        return mColorFilter;
    }

    private void applyColorFilter() {
        if (mBitmapPaint != null) {
            mBitmapPaint.setColorFilter(mColorFilter);
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initializeBitmap() {
        if (mDisableCircularTransformation) {
            mBitmap = null;
        } else {
            mBitmap = getBitmapFromDrawable(getDrawable());
        }
        setup();
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }

        if (mBitmap == null) {
            invalidate();
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mCircleBackgroundPaint.setStyle(Paint.Style.FILL);
        mCircleBackgroundPaint.setAntiAlias(true);
        mCircleBackgroundPaint.setColor(mCircleBackgroundColor);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        mBorderRect.set(calculateBounds());


        mDrawableRect.set(mBorderRect);
        if (!mBorderOverlay && mBorderWidth > 0) {
            /**
             * 当绘制圆角矩形时，圆角四周和border边框会有留白,所以适当增加图片的绘制区域
             */
            mDrawableRect.inset(mBorderWidth - 1f, mBorderWidth - 1f);
        }

        applyColorFilter();
        updateShaderMatrix();
        invalidate();
    }


    /**
     * 就算图片的区域
     *
     * @return
     */
    private RectF calculateBounds() {
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        if (mIsOval) {
            int sideLength = Math.min(availableWidth, availableHeight);
            /**
             * 如果是圆形图片，则选取较短的边作为直径，较长的一边位置居中
             */
            float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
            float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

            return new RectF(left, top, left + sideLength, top + sideLength);

        } else {
            return new RectF(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + availableWidth, getPaddingTop() + availableHeight);
        }

    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class OutlineProvider extends ViewOutlineProvider {

        @Override
        public void getOutline(View view, Outline outline) {
            Rect bounds = new Rect();
            mBorderRect.roundOut(bounds);
            outline.setRoundRect(bounds, bounds.width() / 2.0f);
        }

    }
}
