package com.violin.imageview.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.violin.util.Util

class FlowLayout : ViewGroup {
    private val childBounds = mutableListOf<Rect>()
    val horizontalSpace = Util.dp2px(3f)
    val verticalSpace = Util.dp2px(3f)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val flowWidth = MeasureSpec.getSize(widthMeasureSpec)

        var paddingLeft = paddingLeft
        var paddingRights = paddingRight
        var paddingTop = paddingTop
        var paddingBottom = paddingBottom
        var widthUsed = paddingLeft.toFloat()
        var heightUsed = paddingTop
        var lineHeight = 0
        resolveSize(0, widthMeasureSpec)
        childBounds.clear()
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == View.VISIBLE) {
//                measureChildWithMargins(childView, widthMeasureSpec, widthUsed.toInt(), heightMeasureSpec, heightUsed)
                measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            } else {
                continue
            }
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight
            Log.d("FlowLayout", "childWidth:${childWidth}  childHeight:$childHeight")
            lineHeight = Math.max(lineHeight, childHeight)
            if (widthUsed + childWidth + horizontalSpace + paddingRights > flowWidth) {

                heightUsed += verticalSpace.toInt() + lineHeight
                widthUsed = paddingLeft.toFloat()

                val react = Rect(widthUsed.toInt(), heightUsed, childWidth + widthUsed.toInt(), heightUsed + childHeight)
                childBounds.add(react)

            } else {
                val react = Rect(widthUsed.toInt(), heightUsed, childWidth + widthUsed.toInt(), heightUsed + childHeight)
                childBounds.add(react)
                widthUsed += childWidth + horizontalSpace
            }


        }
        val wantedHeight = heightUsed + lineHeight + paddingBottom
        Log.d("FlowLayout", "floatWidth:${flowWidth} wantedHeight ${wantedHeight}")
        setMeasuredDimension(flowWidth, View.resolveSize(wantedHeight,heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        for (i in 0 until childCount) {
            val rect = childBounds[i]
            getChildAt(i).layout(rect.left, rect.top, rect.right, rect.bottom)

        }


    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        val layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = Util.dp2px(5f).toInt()
        return layoutParams
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }


}