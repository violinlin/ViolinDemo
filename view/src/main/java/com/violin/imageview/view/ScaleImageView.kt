package com.violin.imageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import com.violin.imageview.R

class ScaleImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    val TAG = ScaleImageView::class.java.simpleName
    lateinit var bitmap: Bitmap
    lateinit var gestureDetector: GestureDetectorCompat
    val paint = Paint()
    var bitmapX = 0f
    var bitmapY = 0f
    var scale = 1f
    var isBig = false
    var translateX = 0f
    var translateY = 0f
    val scroller: OverScroller by lazy {
        OverScroller(getContext())
    }

    var scaleFraction = 0f
        set(value) {
            invalidate()
            field = value
        }


    val animator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f).setDuration(500)
    }

    init {
        bitmap = getAvatar()
        paint.isAntiAlias = true
        gestureDetector = GestureDetectorCompat(getContext(), this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        bitmapX = (width - bitmap.width) / 2f
        bitmapY = (height - bitmap.height) / 2f
        val scaleX = width.toFloat() / bitmap.width
        val scaleY = height.toFloat() / bitmap.height
        scale = Math.max(scaleX, scaleY) * 1.5f


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val s = (scale - 1) * scaleFraction + 1f
        canvas?.translate(translateX * scaleFraction, translateY * scaleFraction)
        canvas?.scale(s, s, width / 2f, height / 2f)
        canvas?.drawBitmap(bitmap, bitmapX, bitmapY, paint)
    }


    fun getAvatar(): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.test, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        return BitmapFactory.decodeResource(resources, R.drawable.test, options)
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {

        if (isBig) {
            animator.reverse()
        } else {
            animator.start()
        }
        isBig = !isBig
        Log.d(TAG, "onDoubleTap")
        return true

    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        Log.d(TAG, "onDoubleTapEvent")
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        Log.d(TAG, "onSingleTapConfirmed")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        Log.d(TAG, "onShowPress")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d(TAG, "onSingleTapUp")
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.d(TAG, "onDown")
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        Log.d(TAG, "onFling")
        if (isBig) {
            scroller.fling(translateX.toInt(), translateY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(bitmap.width * scale - width) / 2).toInt(), ((bitmap.width * scale - width) / 2f).toInt(),
                    (-((bitmap.height * scale - height)) / 2).toInt(), ((bitmap.height * scale - height) / 2).toInt(),
                    10, 10
            )
            postOnAnimation(this)//等待下一帧执行
        }
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        if (isBig) {
            translateX -= distanceX
            translateX = Math.min(translateX, (bitmap.width * scale - width) / 2)
            translateX = Math.max(translateX, -(bitmap.width * scale - width) / 2)
            translateY -= distanceY
            translateY = Math.min(translateY, (bitmap.height * scale - height) / 2)
            translateY = Math.max(translateY, -(bitmap.height * scale - height) / 2)
            invalidate()
        }

        Log.d(TAG, "onScroll")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        Log.d(TAG, "onLongPress")
    }

    override fun run() {
        if (scroller.computeScrollOffset()) {
            translateX = scroller.currX.toFloat()
            translateY = scroller.currY.toFloat()
            invalidate()
            postOnAnimation(this)
        }


    }
}