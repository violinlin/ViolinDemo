package com.violin.imageview.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.violin.util.Util
import kotlin.math.cos
import kotlin.math.sin

class ClockView : View {

    val RADIUS = Util.dp2px(150f)
    val LENGTH = Util.dp2px(100f)
    val bounds = RectF()

    val ANGLE = 120

    val paint = Paint();
    val arcPath = Path()
    lateinit var dashPathEffect: PathDashPathEffect;

    constructor(context: Context, attributeset: AttributeSet) : super(context, attributeset)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val centerX = width / 2
        val centerY = height / 2
        bounds.set(centerX - RADIUS, centerY - RADIUS, centerX + RADIUS, centerY + RADIUS)
        arcPath.reset()
        arcPath.addArc(bounds, 90f + ANGLE / 2, 360f - ANGLE)

        val length = PathMeasure(arcPath, false).length

        val dashPath = Path()
        dashPath.addRect(0f, 0f, Util.dp2px(2f), Util.dp2px(10f), Path.Direction.CW)
        dashPathEffect = PathDashPathEffect(dashPath, (length - Util.dp2px(2f)) / 20, 0f, PathDashPathEffect.Style.ROTATE)


    }

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Util.dp2px(2f)
        paint.color = Color.RED
        paint.isAntiAlias = true


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(arcPath, paint)
        paint.pathEffect = dashPathEffect
        canvas?.drawPath(arcPath, paint)
        paint.pathEffect = null

        canvas?.drawLine(width / 2f, height / 2f,
                (cos(Math.toRadians(getAngelMark(6).toDouble())) * LENGTH + width/2).toFloat(),
                (sin(Math.toRadians(getAngelMark(6).toDouble())) * LENGTH + height/2).toFloat(), paint)


    }

    private fun getAngelMark(mark: Int): Int {

        return 90 + ANGLE / 2 + ((360 - ANGLE) / 20) * mark
    }


}