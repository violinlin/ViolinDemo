package com.violin.firstkt

sealed class Result(var msg: String)

class Success (msg: String) : Result(msg)
class Failure(msg: String) : Result(msg)
class Unknow(msg: String) : Result(msg)

fun getResultMsg(result: Result) =
    when (result) {
        is Success -> result.msg
        is Failure -> result.msg
        is Unknow -> TODO()
    }
