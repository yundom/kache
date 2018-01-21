package com.yundom.kache

import com.yundom.kache.config.FIFO
import com.yundom.kache.config.LRU
import com.yundom.kache.config.Policy
import com.yundom.kache.map.FifoMap
import com.yundom.kache.map.LruMap
import com.yundom.kache.store.ObjectCache

typealias Configurations = Builder.() -> Unit

class Builder {
    var policy: Policy = LRU
    var capacity: Int = 128

    companion object {
        fun <K, V> build(config: Configurations? = null): Kache<K, V> {
            return Builder().apply {
                config?.let {
                    apply(it)
                }
            }.build()
        }
    }

    fun <K, V> build(): Kache<K, V> {
        return ObjectCache(capacity, { capacity ->
            when (policy) {
                LRU -> LruMap<K, V>(capacity)
                FIFO -> FifoMap<K, V>(capacity)
            }
        })
    }
}