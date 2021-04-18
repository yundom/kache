package com.yundom.sample

import com.yundom.kache.Builder
import com.yundom.kache.config.LRU

fun main() {
    val kache = Builder.build<Int, String> {
        policy = LRU
        capacity = 16
    }

    (1..100).forEach {
        kache.put(it, "$it")
    }

    val result = kache.get(1)

    assert(result == null)

    println("get(100) = ${kache.get(100)}")
}