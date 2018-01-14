package com.mojopot.kache.map

class FifoMap<K, V>(private val capacity: Int) : MutableMap<K, V> {
    private var map: MutableMap<K, V> = object : LinkedHashMap<K, V>(capacity) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
            return size > capacity
        }
    }

    override val size: Int
        get() = map.size

    override fun containsKey(key: K): Boolean {
        return map.containsKey(key)
    }

    override fun containsValue(value: V): Boolean {
        return map.containsValue(value)
    }

    override fun get(key: K): V? {
        return map.get(key)
    }

    override fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = map.entries
    override val keys: MutableSet<K>
        get() = map.keys
    override val values: MutableCollection<V>
        get() = map.values

    override fun clear() {
        map.clear()
    }

    override fun put(key: K, value: V): V? {
        return map.put(key, value)
    }

    override fun putAll(from: Map<out K, V>) {
        map.putAll(from)
    }

    override fun remove(key: K): V? {
        return map.remove(key)
    }
}