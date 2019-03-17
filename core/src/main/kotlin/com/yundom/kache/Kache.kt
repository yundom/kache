package com.yundom.kache

interface Kache<K, V> {
    /**
     * Gets an value with the given key
     *
     * @param key the key
     * @return the value or `null` if does not exist
     */
    fun get(key: K): V?

    /**
     * Puts a value with the given key
     *
     * @param key the key
     * @param value the value to store
     * @return the previous value associated with the key, or `null` of the key
     * was not present in the cache.
     */
    fun put(key: K, value: V): V?

    /**
     * Removes a value with the given key
     *
     * @param key the key
     * @return the previous value associated with the key, or `null` of the key
     * was not present in the cache.
     */
    fun remove(key: K): V?

    /**
     * Clears the entire cache
     */
    fun clear()

    /**
     * Gets the maximum number of entries of the cache
     *
     * @param the maximum number of entries of the cache
     */
    fun getMaxSize(): Int

    /**
     * Gets the number of entries in the cache
     *
     * @return the number of entries in the cache
     */
    fun getSize(): Int
}