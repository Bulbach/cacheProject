package by.alex.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache<K, V>{
    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, Integer> frequency;
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

    private void evict() {
        int minFreq = frequencyLists.keySet().iterator().next();
        K evictKey = frequencyLists.get(minFreq).iterator().next();
        frequencyLists.get(minFreq).remove(evictKey);
        if (frequencyLists.get(minFreq).isEmpty()) {
            frequencyLists.remove(minFreq);
        }
        cache.remove(evictKey);
        frequency.remove(evictKey);
    }
    private String defaultValue() {
        return "Value not found";
    }
}

