package ru.Kirill112002.sd.lab1;

import org.junit.Assert;
import org.junit.Test;

public class SimpleLruCacheTest {
    @Test
    public void testNull() {
        final LruCache<Integer, String> lruCache = new SimpleLruCache<>(4);
        Assert.assertEquals(0, lruCache.size());
        Assert.assertNull(lruCache.get(10));
    }

    @Test
    public void testAddOne() {
        final LruCache<Integer, String> lruCache = new SimpleLruCache<>(4);
        lruCache.put(1, "1");
        Assert.assertEquals(1, lruCache.size());
        Assert.assertNull(lruCache.get(10));
    }

    @Test
    public void testOnly() {
        final LruCache<Integer, String> lruCache = new SimpleLruCache<>(1);
        lruCache.put(1, "1");
        lruCache.put(2, "2");
        Assert.assertEquals(1, lruCache.size());
        Assert.assertNull(lruCache.get(1));
        Assert.assertEquals("2", lruCache.get(2));
    }

    @Test
    public void testAddLimit() {
        final LruCache<Integer, String> lruCache = new SimpleLruCache<>(129);
        for (int i = 0; i < 128; i++) {
            lruCache.put(i, Integer.toString(i));
        }

        for (int i = 0; i < 128; i++) {
            Assert.assertEquals(Integer.toString(i), lruCache.get(i));
        }

    }

    @Test
    public void testAddMoreLimit() {
        final LruCache<Integer, String> lruCache = new SimpleLruCache<>(10);
        for (int i = 0; i < 100; i++) {
            lruCache.put(i, Integer.toString(i));
        }
        for (int i = 0; i < 90; i++) {
            Assert.assertNull(lruCache.get(i));
        }
        for (int i = 90; i < 100; i++) {
            Assert.assertEquals(Integer.toString(i), lruCache.get(i));
        }
    }

    @Test
    public void testUpdate() {
        final LruCache<Integer, String> lruCache = new SimpleLruCache<>(2);
        lruCache.put(1, "1");
        lruCache.put(2, "2");
        lruCache.get(1);
        lruCache.put(3, "3");
        Assert.assertNotNull(lruCache.get(1));
    }

    @Test
    public void testChangeCapacity() {
        final LruCache<Integer, String> lruCache = new SimpleLruCache<>(2);
        lruCache.put(1, "1");
        lruCache.put(2, "2");
        lruCache.put(3, "3");
        Assert.assertEquals(2, lruCache.size());

        lruCache.changeCapacity(3);
        lruCache.put(4, "4");
        Assert.assertEquals(3, lruCache.size());
    }

    @Test
    public void testChange() {
        final LruCache<Integer, String> lruCache = new SimpleLruCache<>(2);
        lruCache.put(1, "1");
        lruCache.put(2, "2");
        lruCache.put(1, "Text Example");
        Assert.assertEquals("Text Example", lruCache.get(1));
    }
}
