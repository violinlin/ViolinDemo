package com.violin.violindemo.palette

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class RatioImageView : ImageView {

    constructor(context: Context) :super(context)

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = (MeasureSpec.getSize(widthMeasureSpec)/16f*9).toInt()

        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY)

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }
}