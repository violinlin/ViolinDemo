package com.violin.imageview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_typeface.*

/**
 * 加载自定义字体
 */

class TypeFaceActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_typeface)

        val typeface = Typeface.createFromAsset(assets, "ZCOOLKuaiLe-Regular.ttf")
        tv_content.typeface = typeface



    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TypeFaceActivity::class.java)
            context.startActivity(intent)
        }
    }
}