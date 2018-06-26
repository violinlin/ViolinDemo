package com.violin.firstkt.bean

/**
 * Date: 2018/6/25 15:15
 * Author: wanghuilin
 * Mail: huilin_wang@dingyuegroup.cn
 * Desc:
 */
class Book(name: String, price: Double) {
    val VERSION: String = "1.4"

    var bookName: String = name

    lateinit var bookType:String

    var price: Double = price

    init {
        this.price = price
    }

    /**
     * 自定义get方法
     */
    val isExpen: Boolean get() = price > 20

    var author: String = ""
        get() = "作者:" + field

}