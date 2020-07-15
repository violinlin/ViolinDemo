package com.violin.imageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import com.violin.util.Util

class MateriaEditText : androidx.appcompat.widget.AppCompatEditText {

    val hint_text_height = Util.dp2px(20f)
    val hint_text_margin = Util.dp2px(5f)
    val hint_text_offset = Util.dp2px(16f)
    var floatHintShown = false;

    val textPaint = Paint()
    var floatOffset: Float = 0f
    get() {
        invalidate()
        return field
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        setPadding(paddingLeft, (paddingTop + hint_text_height + hint_text_margin).toInt(), paddingRight, paddingBottom)

        textPaint.textSize = hint_text_height
        textPaint.isAntiAlias = true
        textPaint.color = Color.RED
        textPaint.alpha = 0

        val objAnimation = ObjectAnimator.ofFloat(this, "floatOffset", 0f, 1f)
        objAnimation.duration = 200
        objAnimation.startDelay = 0

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (floatHintShown) {
                    if (TextUtils.isEmpty(s)) {
                        objAnimation.reverse()
                        floatHintShown = false
                    }

                } else {
                    if (!TextUtils.isEmpty(s)) {
                        objAnimation.start()
                        floatHintShown = true
                    }
                }
            }

        })
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        textPaint.alpha = (255 * floatOffset).toInt()
        val offset = hint_text_offset * (1 - floatOffset)
        canvas?.drawText(hint.toString(), 0f, hint_text_height + offset, textPaint)

    }
}