package com.yundom.kache

import com.yundom.kache.config.FIFO
import com.yundom.kache.config.LRU
import org.junit.Assert.*
import org.junit.Test

class KacheTest {

    @Test(expected = IllegalArgumentException::class)
    fun testZeroCapacity() {
        Builder.build<Int, String>({
            policy = LRU
            capacity = 0
        })
    }

    @Test
    fun testMaxMemorySize() {
        val kache = Builder.build<Int, String>({
            policy = LRU
            capacity = 16
        })

        assertEquals(16 * 1024 * 1024, kache.getMaxMemorySize())
    }

    @Test
    fun testPutAndGet() {
        val kache = Builder.build<Int, String>({
            policy = LRU
            capacity = 10
        })

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
        val kache = Builder.build<Int, String>({
            policy = LRU
            capacity = 10
        })

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
        val kache = Builder.build<Int, String>({
            policy = LRU
            capacity = 10
        })

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
        val kache = Builder.build<Int, String>({
            policy = LRU
            capacity = 10
        })

        assertNull(kache.put(1, 1.toString()))
        assertEquals(1.toString(), kache.put(1, 2.toString()))
        assertEquals(2.toString(), kache.put(1, 3.toString()))
    }

    @Test
    fun testLruPolicy() {
        val kache = Builder.build<Int, String> {
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
        val kache = Builder.build<Int, String> {
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