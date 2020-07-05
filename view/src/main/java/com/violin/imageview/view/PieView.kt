package com.violin.imageview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.violin.util.Util
import kotlin.math.cos
import kotlin.math.sin

class PieView : View {

    private val RADIUS = Util.dp2px(150f);
    private val OFFSET_LENGTH = Util.dp2px(20f)

    constructor(context: Context, attributeset: AttributeSet) : super(context, attributeset)

    val OUT_INDEX = 2;
    val angles = arrayOf(60f, 120f, 100f, 80f)
    val colors = arrayOf(Color.parseColor("#2979FF"), Color.parseColor("#C2185B"),
            Color.parseColor("#009688"), Color.parseColor("#FF8F00"))
    private val bounds = RectF()
    private val paint = Paint()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val centerX = width / 2
        val centerY = height / 2
        bounds.set(centerX - RADIUS, centerY - RADIUS, centerX + RADIUS, centerY + RADIUS)
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var currentAngle = 0f
        for (i in angles.indices) {
            paint.color = colors[i]
            canvas?.save()
            if (i == OUT_INDEX) {
                canvas?.translate(cos(Math.toRadians(currentAngle.toDouble() + angles[i]/2)).toFloat()*OFFSET_LENGTH,
                        sin(Math.toRadians(currentAngle.toDouble() + angles[i]/2)).toFloat()*OFFSET_LENGTH)
            }
            canvas?.drawArc(bounds, currentAngle, angles[i], true, paint)
            canvas?.restore()
            currentAngle += angles[i]

        }

    }


}