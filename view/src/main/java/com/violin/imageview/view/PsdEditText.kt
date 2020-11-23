package com.violin.imageview.view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * author: wanghuilin
 * created on: 2020/9/27
 * description:
 */
class PsdEditText : AppCompatEditText {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}