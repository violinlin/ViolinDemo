package com.violin.firstkt.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.violin.firstkt.bean.Book
import kotlin.collections.ArrayList

/**
 * Date: 2018/6/25 14:46
 * Author: wanghuilin
 * Mail: huilin_wang@dingyuegroup.cn
 * Desc:
 */
class RVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     var bookList: ArrayList<Book> = ArrayList()

    fun addData(books: ArrayList<Book>) {

        bookList.addAll(books)

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return if (bookList == null) {
            0
        } else {
            bookList.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookItemVH(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, null))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BookItemVH) {
            holder.bind(bookList[position])
        }

    }
}