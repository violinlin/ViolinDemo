package com.violin.violindemo.palette

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.violin.violindemo.R

class RVAdapter : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    private val mData: ArrayList<ImageBean> = ArrayList()

    fun setData(data: ArrayList<ImageBean>) {
        mData.addAll(data)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(PaletteItemView(parent.context))

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemView is PaletteItemView) {
            holder.itemView.setData(mData.get(position))
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}