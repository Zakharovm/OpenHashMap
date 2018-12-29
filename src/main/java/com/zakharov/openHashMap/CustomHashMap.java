package com.zakharov.openHashMap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class CustomHashMap {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private boolean[] isFree;
    private int[] keys;
    private long[] values;
    private int size;
    private float loadFactor;

    /* конструктор по умолчанию */
    public CustomHashMap() {
        this.isFree = new boolean[DEFAULT_INITIAL_CAPACITY];
        this.keys = new int[DEFAULT_INITIAL_CAPACITY];
        this.values = new long[DEFAULT_INITIAL_CAPACITY];
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        Arrays.fill(isFree, true);
    }

    /* конструктор для указания начального размера хешмапы */
    public CustomHashMap(int capacity) {
        this.isFree = new boolean[capacity];
        this.keys = new int[capacity];
        this.values = new long[capacity];
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        Arrays.fill(isFree, true);
    }

    /* кладем элемент с ключом и значением */
    public boolean put(int key, long value) {

        if (size++ > keys.length * loadFactor) {
            resize();
        }

        int position = index(hash(key));

        for (int i = position; ; i++) {
            if (i == keys.length) i = 0;
            if (isFree[i]) keys[i] = key;
            if (keys[i] == key) {
                values[i] = value;
                isFree[i] = false;
                return true;
            }
        }
    }

    /* возвращает значение по ключу */
    public long get(int key) {
        for (int i = index(hash(key)); ; i++) {
            if (i == keys.length) i = 0;
            if (isFree[i]) throw new NoSuchElementException("No such key! -> [" + key + "]");
            if (keys[i] == key) return values[i];
        }
    }

    /* возвращает количество элементов в хешмапе */
    public int size() {
        return size;
    }

    /* увеличение размера хешмапы */
    private void resize() {

        int[] oldTableKeys = keys;
        long[] oldTableValues = values;
        int oldCap = keys.length;
        int newCap = oldCap * 2;

        int[] newTableKeys = new int[newCap];
        long[] newTableValues = new long[newCap];
        boolean[] newTableIsFree = new boolean[newCap];
        Arrays.fill(newTableIsFree, true);
        keys = newTableKeys;
        values = newTableValues;
        isFree = newTableIsFree;

        for (int i = 0; i < oldCap; i++) {
            int key;
            long value;
            if (oldTableKeys[i] != 0) {
                key = oldTableKeys[i];
                value = oldTableValues[i];
                int position = index(hash(key));

                for (int j = position; ; j++) {
                    if (j == keys.length) j = 0;
                    if (isFree[j]) {
                        keys[j] = key;
                        values[j] = value;
                        isFree[j] = false;
                    }
                    if (keys[j] == key) {
                        values[j] = value;
                        isFree[j] = false;
                        break;
                    }
                }

            }
        }
    }

    /* хэш-функция */
    private int hash(int key) {
        key ^= (key >>> 20) ^ (key >>> 12);
        return key ^ (key >>> 7) ^ (key >>> 4);
    }

    /* возвращает номер позиции по значению хэш-функции */
    private int index(int hash) {
        return Math.abs(hash) & (keys.length - 1);
    }

    /* метод для отображения содержания хешмапы */
    public void output() {
        for (int i = 0; i < keys.length; i++) {
            if (!isFree[i])
            System.out.println("key: " + keys[i] + " value: " + values[i]);
        }
    }
}

