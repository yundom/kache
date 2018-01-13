package com.mojopot.kache

import org.junit.Assert.assertEquals
import org.junit.Test

class LruCacheTest {

    @Test(expected = IllegalArgumentException::class)
    fun testZeroCapacity() {
        LruCache<String, String>(0)
    }

    @Test
    fun testMaxMemorySize() {
        val kache: Kache<String, String> = LruCache(10)
        assertEquals(10 * 1024 * 1024, kache.getMaxMemorySize())
    }


}