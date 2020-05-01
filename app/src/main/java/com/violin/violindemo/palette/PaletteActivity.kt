package com.violin.violindemo.palette

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.violin.util.Util
import com.violin.violindemo.R
import kotlinx.android.synthetic.main.activity_palette.*
import java.util.ArrayList
import androidx.palette.graphics.Palette
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import android.graphics.Color
import com.google.android.material.appbar.AppBarLayout


class PaletteActivity : AppCompatActivity() {

    var lastPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)

        initView()
    }

    private fun initView() {


        setSupportActionBar(tool_bar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tool_bar.setNavigationOnClickListener {
            finish()
        }


        val linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)


        recyclerView.layoutManager = linearLayoutManager
        val rvAdapter = RVAdapter()
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            Log.d(TAG, "appbar verOffset $verticalOffset")
            if (verticalOffset == 0) {
                lastPosition = -1
                setUiColor("picture1")
            }
        })

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                val space = Util.dp2px(view.context, 5)
                if (position == 0) {
                    outRect.top = space
                }
                outRect.bottom = space
            }
        })


        recyclerView.adapter = rvAdapter
        val data = ArrayList<ImageBean>()

        for (i in 1..15) {
            data.add(ImageBean("picture${i % 5 + 1}"))
        }
        rvAdapter.setData(data)



        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.d(TAG, "recyclerView scroll -- dx:$dx dy:$dy y:${recyclerView?.y.toString()}")

                val position = linearLayoutManager.findFirstVisibleItemPosition()
                if (position != lastPosition && dy != 0) {
                    val path = data.get(position).path


                    setUiColor(path)

                    lastPosition = position
                }


            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })


    }

    private fun setUiColor(path: String) {

        Glide.with(this).asBitmap().load(resources.getIdentifier(path, "drawable", packageName))
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {


                        androidx.palette.graphics.Palette.from(resource).generate { palette ->
                            palette?.let {

                                val dominantSwatch = it.dominantSwatch
                                //
                                collapsing_tool_bar.setCollapsedTitleTextColor(dominantSwatch!!.bodyTextColor)
                                //

                                collapsing_tool_bar.setExpandedTitleColor(it.darkVibrantSwatch?.bodyTextColor?:Color.WHITE)

                                tool_bar.navigationIcon?.let {
                                    it.clearColorFilter()
                                    it.setColorFilter(dominantSwatch.bodyTextColor, android.graphics.PorterDuff.Mode.MULTIPLY)

                                }

                                collapsing_tool_bar.setContentScrimColor(dominantSwatch!!.rgb)

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    window.statusBarColor = changeColor(dominantSwatch!!.rgb)
                                }

                            }
                        }
                    }

                })


    }

    private fun changeColor(rgb: Int): Int {
        var red = rgb shr 16 and 0xFF
        var green = rgb shr 8 and 0xFF
        var blue = rgb and 0xFF
        red = Math.floor(red * (1 - 0.2)).toInt()
        green = Math.floor(green * (1 - 0.2)).toInt()
        blue = Math.floor(blue * (1 - 0.2)).toInt()
        return Color.rgb(red, green, blue)
    }


    companion object {

        val TAG = PaletteActivity::class.java.simpleName!!
        fun start(context: Context) {
            val intent = Intent(context, PaletteActivity::class.java)
            context.startActivity(intent)
        }
    }
}
