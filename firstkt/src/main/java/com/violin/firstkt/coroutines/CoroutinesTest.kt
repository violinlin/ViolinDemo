package com.violin.firstkt.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


suspend fun fetchData(): String {
    delay(1000) // 模拟网络请求，挂起1秒
    return "Data fetched"
}

fun main() = runBlocking {
    val result = fetchData() // 调用挂起函数
    println(result)
}
