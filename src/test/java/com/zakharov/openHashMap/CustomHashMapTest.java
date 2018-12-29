package com.zakharov.openHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class CustomHashMapTest {
    private CustomHashMap customHashMap;

    @Before
    public void setUp() throws Exception {
        customHashMap = new CustomHashMap();
        customHashMap.put(32, 1L);
        customHashMap.put(22, 2L);
        customHashMap.put(15, 3L);
        customHashMap.put(20, 5L);

    }

    @Test
    public void putTest() throws Exception {
        assertTrue(customHashMap.put(20, 5L));
    }

    @Test
    public void getTest() throws Exception {
        long value = customHashMap.get(15);
        assertEquals(3L, value);

    }

    @Test
    public void sizeTest() throws Exception {
        int size = customHashMap.size();
        assertEquals(4, size);
    }

    @Test(expected = NoSuchElementException.class)
    public void getWrongKeyTest() throws Exception {
        customHashMap.get(100);
    }
}
