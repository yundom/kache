package com.yundom.kache.policy

import com.yundom.kache.Kache
import com.yundom.kache.map.FifoMap
import java.lang.IllegalStateException

open class ObjectCache<K, V>(private val capacity: Int = DEFAULT_CAPACITY) : Kache<K, V> {
    companion object {
        private val DEFAULT_CAPACITY = 10
        private val REMOVE_ALL = -1
    }

    private val maxMemorySize = capacity * 1024 * 1024
    private var memorySize: Int = 0
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
        val previous = map.put(key, value)
        memorySize += getValueSize(value)
        previous?.let {
            memorySize -= getValueSize(it)
        }
        trimToSize(maxMemorySize)
        return previous
    }

    @Synchronized override fun remove(key: K): V? {
        val previous = map.remove(key)
        previous?.run {
            memorySize -= getValueSize(previous)
        }
        return previous
    }

    override fun clear() {
        trimToSize(REMOVE_ALL)
    }

    override fun getMaxMemorySize(): Int {
        return maxMemorySize
    }

    override fun getMemorySize(): Int {
        return memorySize
    }

    protected fun getValueSize(value: V): Int {
        return 1
    }

    private fun trimToSize(maxSize: Int) {
        while (true) {
            if (memorySize <= maxSize || map.isEmpty()) {
                break
            }

            if (memorySize < 0 || (map.isEmpty() && memorySize != 0)) {
                throw IllegalStateException(javaClass.name + ".getValueSize() is reporting inconsistent results")
            }

            val toRemove = map.entries.iterator().next()
            map.remove(toRemove.key)
            memorySize -= getValueSize(toRemove.value)
        }
    }
}