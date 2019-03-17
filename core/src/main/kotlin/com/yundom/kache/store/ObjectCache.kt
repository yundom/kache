package com.yundom.kache.store

import com.yundom.kache.Kache
import com.yundom.kache.map.FifoMap

open class ObjectCache<K, V>(private val capacity: Int = DEFAULT_CAPACITY) : Kache<K, V> {
    companion object {
        private val DEFAULT_CAPACITY = 10
    }

    private val maxSize = capacity
    private var map: MutableMap<K, V>

    init {
        if (capacity <= 0) {
            throw IllegalArgumentException("capacity <= 0")
        }
        map = FifoMap(capacity)
    }

    constructor() : this(DEFAULT_CAPACITY)

    constructor(capacity: Int, getMap: (Int) -> MutableMap<K, V>) : this(capacity) {
        if (this@ObjectCache.capacity <= 0) {
            throw IllegalArgumentException("capacity <= 0")
        }
        map = getMap(capacity)
    }

    @Synchronized override fun get(key: K): V? {
        return map[key]
    }

    @Synchronized override fun put(key: K, value: V): V? {
        return map.put(key, value)
    }

    @Synchronized override fun remove(key: K): V? {
        return map.remove(key)
    }

    override fun clear() {
        map.clear()
    }

    override fun getMaxSize(): Int {
        return maxSize
    }

    override fun getSize(): Int {
        return map.size
    }
}