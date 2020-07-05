package com.violin.imageview.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.violin.imageview.R
import com.violin.util.Util


class AvatarView : View {
    val bounds = RectF()
    val RADIUT = Util.dp2px(150f)
    val paint = Paint()
    lateinit var bitmap: Bitmap

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        paint.isAntiAlias = true
        paint.strokeWidth = Util.dp2px(2f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val centerX = width / 2
        val centerY = height / 2
        bounds.set(centerX - RADIUT, centerY - RADIUT, centerX + RADIUT, centerY + RADIUT)
        bitmap = getAvatar(RADIUT.toInt())

        val matrix = Matrix()
        matrix.setScale(bounds.width()/bitmap.width,bounds.height()/bitmap.height)
        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val layer = canvas?.saveLayer(bounds,paint)
        canvas?.drawOval(bounds, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas?.drawBitmap(bitmap, width / 2 - RADIUT , height / 2 - RADIUT, paint)

        paint.xfermode = null;
        layer?.let {
            canvas?.restoreToCount(it)
        }

    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.test, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.test, options)
    }
}