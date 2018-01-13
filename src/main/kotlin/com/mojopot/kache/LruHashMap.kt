package com.mojopot.kache

class LruHashMap<K, V>(private val capacity: Int) : LinkedHashMap<K, V>(capacity) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean = size > capacity
}