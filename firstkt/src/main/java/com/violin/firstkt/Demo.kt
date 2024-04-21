package com.violin.firstkt

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.concurrent.thread
import kotlin.reflect.KProperty

class Demo {
    lateinit var name: String;//延迟初始化
    fun init() {
        if (!::name.isInitialized) {
            // 判断延迟属性是否初始化
            name = "whl"
        }
        "whl".letterCount {
            var count = 0
            for (char in this) {
                if (char.isLetter()) {
                    count++
                }
            }
            count

        }
        val context: Context? = null;
        val list: MutableList<out View> = ArrayList<Button>()

        val list2: MutableList<in TextView> = ArrayList<View>()
        val any: Any? = list2[0]


    }


}

// 拓展函数
fun String.letterCount(block: String.() -> Int): Int {
    return block.invoke(this)
}

//高级函数
fun operate(num1: Int, num2: Int, result: (Int, Int) -> Int): Int {
    return result(num1, num2)
}

fun add(num1: Int, num2: Int): Int {
    return num1 + num2;
}

fun minus(num1: Int, num2: Int): Int {
    return num1 - num2;
}


fun test() {

    operate(1, 2, ::minus)
    operate(1, 2, ::add)
    operate(1, 2) { num1, num2 ->
        num1 - num2
    }
}

//内联函数
inline fun <reified T> test(t: T, crossinline block: () -> Unit) {
    val classreal = T::class.java
    thread {
        Runnable {
            block.invoke()
        }
    }
}

class MySet1<T>(private val realSet: HashSet<T>) : Set<T> {
    override val size: Int
        get() = realSet.size

    override fun isEmpty(): Boolean {
        return realSet.isEmpty()
    }

    override fun iterator(): Iterator<T> {
        return realSet.iterator()
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return realSet.containsAll(elements)
    }

    override fun contains(element: T): Boolean {
        return realSet.contains(element)
    }

}

// 类代理
class MySet<T>(private val realSet: HashSet<T>) : Set<T> by realSet {

}

// 属性委托
class Lazyclass() {
    val name: String by lazy {
        "name"
    }
    var delegatevalue by Delegate()

}

// 委托实现类
class Delegate {
    lateinit var realValue: Any;
    operator fun getValue(lazyclass: Lazyclass, property: KProperty<*>): Any {
        return realValue
    }

    operator fun setValue(lazyclass: Lazyclass, property: KProperty<*>, any: Any) {
        realValue = any

    }

}

