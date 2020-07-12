package com.violin.imageview.view

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager


class TwoPageView(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    var downX = 0f
    var downY = 0f
    var downScrollX = 0f
    val scroller: Scroller by lazy {
        Scroller(getContext())
    }
    val velocityTracker: VelocityTracker by lazy {
        VelocityTracker.obtain()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var result = false
        ev?.let {
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downX = it.x
                    downY = it.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = it.x - downX
                    if (dx > ViewConfiguration.get(context).scaledPagingTouchSlop) {
                        result = true
                    }
                }

            }
        }

        return result
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)

        ev?.let {
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downX = it.x
                    downY = it.y
                    downScrollX = scrollX.toFloat()

                }
                MotionEvent.ACTION_MOVE -> {
                    var dx = downX - it.x + downScrollX
                    if (dx > width) {
                        dx = width.toFloat()
                    } else if (dx < 0) {
                        dx = 0f
                    }
                    scrollTo(dx.toInt(), 0)

                }
                MotionEvent.ACTION_UP -> {

                    velocityTracker.computeCurrentVelocity(1000, ViewConfiguration.get(context).scaledMaximumFlingVelocity.toFloat())
                    val vx = velocityTracker.xVelocity

                    var targetPage = 0
                    if (Math.abs(vx) < ViewConfiguration.get(context).scaledMinimumFlingVelocity) {
                        targetPage = if (scrollX > width / 2) 1 else 0
                    } else {
                        targetPage = if (vx < 0) 1 else 0
                    }

                    val scrollDistance = if (targetPage == 1) {
                        width - scrollX
                    } else {
                        -scrollX
                    }


                    scroller.startScroll(scrollX, 0, scrollDistance, 0)
                    postInvalidateOnAnimation()

                }
                else -> {

                }

            }
        }

        return true
    }

    override fun computeScroll() {

        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidateOnAnimation()
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childRight = width
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            child.layout(childLeft, 0, childRight, height)
            childLeft += width
            childRight += width
        }
    }

}