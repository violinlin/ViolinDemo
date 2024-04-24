package com.violin.imageview.view

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.violin.util.Util

class SportsView : View {
    val radius = Util.dp2px(80f)
    var progress = 0f
        set(value) {
            field = value
            invalidate()
        }
    var arcRectF = RectF()
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var count:Int = 0;

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        paint.textSize = Util.dp2px(40f)
        paint.textAlign = Paint.Align.CENTER
        setOnClickListener { // 创建 ObjectAnimator 对象
            val holder1 = PropertyValuesHolder.ofFloat("progress", 0f, 100f)
            val holder2 = PropertyValuesHolder.ofFloat("translationX", 300f)
            val animatorProcess =
                ObjectAnimator.ofPropertyValuesHolder(this@SportsView, holder1, holder2)
            animatorProcess.duration = 1000
            animatorProcess.start()
            count++
            Toast.makeText(context,"toast:($count)",Toast.LENGTH_SHORT).show()
            Log.d("SportView","toast")
        }
    }


    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        paint.color = Color.parseColor("#E91E63")
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = Util.dp2px(15f)
        arcRectF[centerX - radius, centerY - radius, centerX + radius] = centerY + radius
        canvas.drawArc(arcRectF, 135f, progress * 3.6f, false, paint)
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        canvas.drawText(
            progress.toInt().toString() + "%",
            centerX,
            centerY - (paint.ascent() + paint.descent()) / 2,
            paint
        )
    }
}