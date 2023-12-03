package by.alex.cache.impl;

import by.alex.cache.AbstractCache;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class LRUCache<K, V> implements AbstractCache<K, V> {

    /**
     * Переменная, которая определяет максимальный размер кэша.
     */
    private final int capacity;
    /**
     * Используется для хранения ключей и значений элементов кэша.
     */
    private final Map<K, V> cache;
    /**
     * Для сохранения порядка использования
     */
    private final Set<K> keyOrder;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.keyOrder = new LinkedHashSet<>();
    }

    public V get(K key) {
        return Optional.ofNullable(cache.get(key))
                .map(value -> {
                    updateKeyOrder(key);
                    return value;
                })
                .orElse(null);
    }

    public void put(K key, V value) {
        if (capacity == 0) {
            return;
        }
        cache.compute(key, (k, v) -> {
            if (v != null) {
                updateKeyOrder(k);
                return value;
            } else {
                if (cache.size() >= capacity) {
                    evict();
                }
                keyOrder.add(key);
                return value;
            }
        });
    }

    public Collection<V> getAllValues() {
        return cache.values();
    }

    private void updateKeyOrder(K key) {
        keyOrder.remove(key);
        keyOrder.add(key);
    }

    public void delete(K key) {
        cache.remove(key);
        keyOrder.remove(key);
    }

    public void evict() {
        K evictKey = keyOrder.iterator().next();
        keyOrder.remove(evictKey);
        cache.remove(evictKey);
    }

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }
}