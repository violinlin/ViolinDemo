package com.violin.violindemo.palette

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.violin.violindemo.R
import kotlinx.android.synthetic.main.palette_item_layout.view.*

class PaletteItemView : FrameLayout {

    constructor(context: Context) : super(context) {
        View.inflate(context, R.layout.palette_item_layout, this)
        layoutParams = LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    fun setData(bean: ImageBean) {

        Glide.with(context).asBitmap().load(resources.getIdentifier(bean.path, "drawable", context.packageName))
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        iv_imageview.setImageBitmap(resource)
                        setUIColor(resource)


                    }

                })


    }

    private fun setUIColor(bitmap: Bitmap) {


        Palette.from(bitmap).generate { palette ->

                    val dominantSwatch = palette.dominantSwatch
                    dominantSwatch?.let {
                        tv_dominant1.setBackgroundColor(it.rgb)
                        tv_dominant2.setBackgroundColor(it.rgb)
                        tv_dominant3.setBackgroundColor(it.rgb)

                        tv_dominant1.setTextColor(it.bodyTextColor)

                        tv_dominant2.text = "titleTextColor"
                        tv_dominant2.setTextColor(it.titleTextColor)

                        tv_dominant3.text = "bodyTextColor"
                        tv_dominant3.setTextColor(it.bodyTextColor)


                    }

                    val mutedSwatch = palette.mutedSwatch
                    mutedSwatch?.let {
                        tv_muted.setTextColor(it.bodyTextColor)
                        tv_muted.setBackgroundColor(it.rgb)
                    }
                    val darkMutedSwatch = palette.darkMutedSwatch
                    darkMutedSwatch?.let {
                        tv_dark_muted.setTextColor(it.bodyTextColor)
                        tv_dark_muted.setBackgroundColor(it.rgb)

                    }

                    val lightMutedSwatch = palette.lightMutedSwatch

                    lightMutedSwatch?.let {
                        tv_light_muted.setTextColor(it.bodyTextColor)
                        tv_light_muted.setTextColor(it.rgb)
                    }


                    val vibrantSwatch = palette.vibrantSwatch
                    vibrantSwatch?.let {
                        tv_vibrant.setTextColor(it.bodyTextColor)
                        tv_vibrant.setBackgroundColor(it.rgb)
                    }

                    val darkVibrantSwatch = palette.darkVibrantSwatch
                    darkVibrantSwatch?.let {
                        tv_dark_vibrant.setTextColor(it.bodyTextColor)
                        tv_dark_vibrant.setBackgroundColor(it.rgb)
                    }

                    val lightVibrantSwatch = palette.lightVibrantSwatch
                    lightVibrantSwatch?.let {
                        tv_light_vibrant.setTextColor(it.bodyTextColor)
                        tv_light_vibrant.setBackgroundColor(it.rgb)
                    }


                }

    }


}