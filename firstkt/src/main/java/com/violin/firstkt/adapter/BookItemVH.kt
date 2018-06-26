package com.violin.firstkt.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.violin.firstkt.bean.Book

/**
 * Date: 2018/6/25 15:33
 * Author: wanghuilin
 * Mail: huilin_wang@dingyuegroup.cn
 * Desc:
 */
class BookItemVH(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    private val textTV = itemView!!.findViewById<TextView>(android.R.id.text1)
    fun bind(book: Book) {
        textTV.text = book.bookName
        if (book.isExpen) {
            textTV.text = textTV.text as String + "贵的"
        } else {
            textTV.text = textTV.text as String + book.author
        }

    }

}