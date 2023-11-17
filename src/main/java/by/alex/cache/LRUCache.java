package by.alex.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final Set<K> keyOrder;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.keyOrder = new LinkedHashSet<>();
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            updateKeyOrder(key);
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
            updateKeyOrder(key);
        } else {
            if (cache.size() >= capacity) {
                evict();
            }
            cache.put(key, value);
            keyOrder.add(key);
        }
    }

    private void updateKeyOrder(K key) {
        keyOrder.remove(key);
        keyOrder.add(key);
    }

    private void evict() {
        K evictKey = keyOrder.iterator().next();
        keyOrder.remove(evictKey);
        cache.remove(evictKey);
    }
}