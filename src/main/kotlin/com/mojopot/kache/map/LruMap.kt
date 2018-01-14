package com.mojopot.kache.map

class LruMap<K, V>(private val capacity: Int) : MutableMap<K, V> {
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
        val value = map.get(key)
        value?.run {
            map.remove(key)
            map.put(key, value)
        }
        return value
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
        val previous = map.get(key)
        map.remove(key)
        map.put(key, value)
        return previous
    }

    override fun putAll(from: Map<out K, V>) {
        from.forEach { key, _ ->
            map.remove(key)
        }
        map.putAll(from)
    }

    override fun remove(key: K): V? {
        return map.remove(key)
    }
}