package com.violin.imageview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.violin.util.Util
import kotlin.random.Random

class ShapeTextView : View {

    //    val textSize = arrayOf( Util.dp2px(20f),Util.dp2px(16f),Util.dp2px(18f),Util.dp2px(25f))
    val textSize = arrayOf(Util.dp2px(60f))
    val paint = Paint()
    val colors = arrayOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.RED, Color.GREEN)

    val size = textSize[Random.nextInt(textSize.size)]

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(size.toInt() * 2 + paddingLeft + paddingRight, size.toInt() + paddingBottom + paddingTop)
    }

    init {
        val padding = Util.dp2px(2f).toInt()
        setPadding(padding, padding, padding, padding)
        paint.color = colors[Random.nextInt(colors.size)]

    }

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        parent.requestDisallowInterceptTouchEvent(true)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var downX = 0f
        ev?.let {
            when (ev?.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = it.getX()

                }
                MotionEvent.ACTION_MOVE -> {
                    if (Math.abs(it.getX() - downX) < ViewConfiguration.get(context).scaledTouchSlop) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(false)
                    }

                }
            }
        }

        Log.d("ShapeTextView", "dispatchTouchEvent ${ev?.action}")
        return super.dispatchTouchEvent(ev)
    }


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("ShapeTextView", "onTouchEvent ${ev?.action}")
        return true
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(paddingLeft.toFloat(), paddingTop.toFloat(), paddingRight.toFloat() + size * 2, paddingBottom.toFloat() + size, Util.dp2px(3f), Util.dp2px(3f), paint)
    }
}