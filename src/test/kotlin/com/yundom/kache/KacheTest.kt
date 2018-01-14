package com.yundom.kache

import com.yundom.kache.map.FifoMap
import com.yundom.kache.map.LruMap
import com.yundom.kache.policy.ObjectCache
import org.junit.Assert.*
import org.junit.Test

class KacheTest {

    @Test(expected = IllegalArgumentException::class)
    fun testZeroCapacity() {
        ObjectCache<String, String>(0)
    }

    @Test
    fun testMaxMemorySize() {
        val kache: Kache<String, String> = ObjectCache(10)
        assertEquals(10 * 1024 * 1024, kache.getMaxMemorySize())
    }

    @Test
    fun testPutAndGet() {
        val kache = ObjectCache<Int, String>(10)

        for (i in 1..10) {
            assertNull(kache.put(i, i.toString()))
        }

        for (i in 1..10) {
            val e = kache.get(i)
            assertNotNull(e)
            assertEquals(i.toString(), e)
        }
    }

    @Test
    fun testRemove() {
        val kache = ObjectCache<Int, String>(10)
        for (i in 1..10) {
            assertNull(kache.put(i, i.toString()))
        }

        val size = kache.getMemorySize()
        assertEquals(10, size)
        val previous = kache.remove(4)
        assertEquals(previous, 4.toString())
        assertEquals(size - 1, kache.getMemorySize())

        kache.clear()
        assertEquals(0, kache.getMemorySize())
    }

    @Test
    fun testDuplicates() {
        val kache = ObjectCache<Int, String>(10)

        for (i in 1..10) {
            assertNull(kache.put(i, i.toString()))
        }

        val memorySize = kache.getMemorySize()

        assertTrue(memorySize == 10)

        for (i in 1..10) {
            assertNotNull(kache.put(i, i.toString()))
        }

        assertEquals(memorySize, kache.getMemorySize())
    }

    @Test
    fun testPreviousValue() {
        val kache = ObjectCache<Int, String>(10)
        assertNull(kache.put(1, 1.toString()))
        assertEquals(1.toString(), kache.put(1, 2.toString()))
        assertEquals(2.toString(), kache.put(1, 3.toString()))
    }

    @Test
    fun testLruRule() {
        val kache = ObjectCache<Int, String>(10, { capacity ->
            LruMap(capacity)
        })

        for (i in 1..10) {
            assertNull(kache.put(i, i.toString()))
        }

        kache.put(11, 11.toString())
        assertNull(kache.get(1))

        kache.get(2)
        kache.put(12, 12.toString())
        assertNotNull(kache.get(2))
        assertNull(kache.get(3))
    }

    @Test
    fun testFifoRule() {
        val kache = ObjectCache<Int, String>(10, { capacity ->
            FifoMap(capacity)
        })

        for (i in 1..10) {
            assertNull(kache.put(i, i.toString()))
        }

        kache.put(11, 11.toString())
        assertNull(kache.get(1))

        kache.get(2)
        kache.put(12, 12.toString())
        assertNull(kache.get(2))
    }
}