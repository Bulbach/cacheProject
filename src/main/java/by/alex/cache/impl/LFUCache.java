package by.alex.cache.impl;

import by.alex.cache.AbstractCache;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache<K, V> implements AbstractCache<K, V> {

    /**
     * Переменная, которая определяет максимальный размер кэша.
     */
    private final int capacity;
    /**
     * Используется для хранения ключей и значений элементов кэша.
     */
    private final Map<K, V> cache;
    /**
     * Используется для отслеживания частоты использования каждого элемента кэша.
     * Ключом является элемент кэша, а значением - его частота использования.
     */
    private final Map<K, Integer> frequency;
    /**
     * Используется для группировки элементов кэша
     * по их частоте использования. Ключом является частота использования,
     * а значением - LinkedHashSet элементов(для сохранения порядка элементов)
     * имеющих данную частоту использования.
     */
    private final Map<Integer, LinkedHashSet<K>> frequencyLists;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.frequency = new HashMap<>();
        this.frequencyLists = new HashMap<>();
        this.frequencyLists.put(1, new LinkedHashSet<>());
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            updateFrequency(key);
            return cache.get(key);
        }
        return null;
    }

    public void put(K key, V value) {
        if (capacity == 0) {
            return;
        }
        if (cache.containsKey(key)) {
            cache.put(key, value);
            updateFrequency(key);
        } else {
            if (cache.size() >= capacity) {
                evict();
            }
            cache.put(key, value);
            frequency.put(key, 1);
            frequencyLists.get(1).add(key);
        }
    }

    @Override
    public Collection<V> getAllValues() {
        return cache.values();
    }

    private void updateFrequency(K key) {
        int freq = frequency.get(key);
        frequency.put(key, freq + 1);
        frequencyLists.get(freq).remove(key);
        if (!frequencyLists.containsKey(freq + 1)) {
            frequencyLists.put(freq + 1, new LinkedHashSet<>());
        }
        frequencyLists.get(freq + 1).add(key);
        if (frequencyLists.get(freq).isEmpty()) {
            frequencyLists.remove(freq);
        }
    }
    public void delete(K key) {
        if (cache.containsKey(key)) {
            int freq = frequency.get(key);
            frequencyLists.get(freq).remove(key);
            if (frequencyLists.get(freq).isEmpty()) {
                frequencyLists.remove(freq);
            }
            cache.remove(key);
            frequency.remove(key);
        }
    }

    public void evict() {
        int minFreq = frequencyLists.keySet().iterator().next();
        K evictKey = frequencyLists.get(minFreq).iterator().next();
        frequencyLists.get(minFreq).remove(evictKey);
        if (frequencyLists.get(minFreq).isEmpty()) {
            frequencyLists.remove(minFreq);
        }
        cache.remove(evictKey);
        frequency.remove(evictKey);
    }

    public boolean containsKey(K id) {
        return cache.containsKey(id);
    }
    private String defaultValue() {
        return "Value not found";
    }
}

