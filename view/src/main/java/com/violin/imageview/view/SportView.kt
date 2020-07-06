package com.violin.imageview.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.violin.util.Util

class SportView : View {
    val bounds = RectF()
    val paint: Paint = Paint()
    val radius = Util.dp2px(150f)
    var centerX = 0;
    var centerY = 0;

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2
        centerY = height / 2
        bounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }

    init {
        paint.isAntiAlias = true


        paint.strokeWidth = Util.dp2px(10f)

        paint.textAlign = Paint.Align.CENTER

        paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,60f,resources.displayMetrics)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.color = Color.GRAY
        canvas?.drawOval(bounds, paint)

        paint.color = Color.RED
        paint.strokeWidth = Util.dp2px(10f)
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawArc(bounds, -60f, 180f, false, paint)

        paint.style = Paint.Style.FILL
        val react = Rect()
        paint.getTextBounds("abc",0,"abc".length,react)

        val offset = react.top + react.bottom
        canvas?.drawText("abc", centerX.toFloat(),centerY.toFloat(),paint)

    }


}