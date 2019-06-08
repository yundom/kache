package com.yundom.kache

import com.yundom.kache.config.FIFO
import com.yundom.kache.config.LRU
import org.junit.Assert.*
import org.junit.Test

class KacheTest {

    @Test(expected = IllegalArgumentException::class)
    fun testZeroCapacity() {
        Builder.build<Int, String> {
            policy = LRU
            capacity = 0
        }
    }

    @Test
    fun testDefaultConfigurations() {
        val kache: Kache<Int, String> = Builder.build()

        assertEquals(128, kache.getMaxSize())
    }

    @Test
    fun testMaxMemorySize() {
        val kache: Kache<Int, String> = Builder.build {
            policy = LRU
            capacity = 16
        }

        assertEquals(16, kache.getMaxSize())
    }

    @Test
    fun testPutAndGet() {
        val kache: Kache<Int, String> = Builder.build {
            policy = LRU
            capacity = 10
        }

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
    fun testExistKey() {
        val kache: Kache<Int, String> = Builder.build {
            policy = LRU
            capacity = 10
        }

        for (i in 1..10) {
            assertNull(kache.put(i, i.toString()))
        }

        assertTrue(kache.exist(5))
        assertFalse(kache.exist(11))
    }

    @Test
    fun testRemove() {
        val kache: Kache<Int, String> = Builder.build {
            policy = LRU
            capacity = 10
        }

        for (i in 1..10) {
            assertNull(kache.put(i, i.toString()))
        }

        val size = kache.getSize()
        assertEquals(10, size)
        val previous = kache.remove(4)
        assertEquals(previous, 4.toString())
        assertEquals(size - 1, kache.getSize())

        kache.clear()
        assertEquals(0, kache.getSize())
    }

    @Test
    fun testDuplicates() {
        val kache: Kache<Int, String> = Builder.build {
            policy = LRU
            capacity = 10
        }

        for (i in 1..10) {
            assertNull(kache.put(i, i.toString()))
        }

        val memorySize = kache.getSize()

        assertTrue(memorySize == 10)

        for (i in 1..10) {
            assertNotNull(kache.put(i, i.toString()))
        }

        assertEquals(memorySize, kache.getSize())
    }

    @Test
    fun testPreviousValue() {
        val kache: Kache<Int, String> = Builder.build {
            policy = LRU
            capacity = 10
        }

        assertNull(kache.put(1, 1.toString()))
        assertEquals(1.toString(), kache.put(1, 2.toString()))
        assertEquals(2.toString(), kache.put(1, 3.toString()))
    }

    @Test
    fun testLruPolicy() {
        val kache: Kache<Int, String> = Builder.build {
            policy = LRU
            capacity = 10
        }

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
    fun testFifoPolicy() {
        val kache: Kache<Int, String> = Builder.build {
            policy = FIFO
            capacity = 10
        }

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