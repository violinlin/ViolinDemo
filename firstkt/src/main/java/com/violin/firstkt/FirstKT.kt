package com.violin.firstkt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.violin.firstkt.adapter.BaseUtil
import com.violin.firstkt.adapter.RVAdapter
import com.violin.firstkt.bean.Book

/**
 * Created by wanghuilin on 2018/6/23.
 *
 * email:wanghuilin@zshiliu.com
 */
class FirstKT : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)
        val base = BaseUtil("hello");
        base.name
        initView()
    }

    private fun initView() {
        val btnKT = findViewById<Button>(R.id.btn_kt)

        btnKT.setOnClickListener {
            Toast.makeText(it.context, "", Toast.LENGTH_SHORT).show()
            Log.d("whl", it.toString())
        }

        btnKT.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                Toast.makeText(view.context, "获取焦点", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(view.context, "丢失焦点", Toast.LENGTH_SHORT).show()
            }
        }

        val phoneET = findViewById<EditText>(R.id.et_phone)

        phoneET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
        val adapter = RVAdapter()
        recyclerView.adapter = adapter

        var mBooks = ArrayList<Book>()
        for (i in 1..10) {
            val book = Book("book" + i, (5 * i).toDouble())
            book.author = i.toString()
            mBooks.add(book)

        }
        adapter.addData(mBooks)


    }

    fun start(context: Context) {
        val intent = Intent(context, FirstKT::class.java)
        context.startActivity(intent)
    }
}