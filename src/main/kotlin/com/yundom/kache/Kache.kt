package com.yundom.kache

interface Kache<K, V> {
    fun get(key: K): V?

    fun put(key: K, value: V): V?

    fun remove(key: K): V?

    fun clear()

    fun getMaxMemorySize(): Int

    fun getMemorySize(): Int
}