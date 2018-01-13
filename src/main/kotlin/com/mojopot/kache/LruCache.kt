package com.mojopot.kache

class LruCache<K, V>(private val capacity: Int = 10) : Kache<K, V> {
    companion object {
        private val DEFAULT_CAPACITY = 10
    }

    private val maxMemorySize = capacity * 1024 * 1024
    private var map: Map<K, V>

    init {
        if (capacity <= 0) {
            throw IllegalArgumentException("capacity <= 0")
        }
        map = LruHashMap(capacity)
    }

    constructor() : this(DEFAULT_CAPACITY)

    override fun get(key: K): V {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun put(key: K, value: V): V {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(key: K): V {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMaxMemorySize(): Int {
        return maxMemorySize
    }

    override fun getMemorySize(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}