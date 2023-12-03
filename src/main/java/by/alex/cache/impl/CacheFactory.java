package by.alex.cache.impl;

import by.alex.cache.AbstractCache;
import by.alex.dto.WagonDto;
import by.alex.util.ApplicationProperties;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.UUID;

@UtilityClass
public class CacheFactory {

    private static final String CAPACITY_KEY ="max-size";
    private static final String ALGORITHM_KEY = "algorithm";


    public static AbstractCache<UUID, WagonDto> createCache() {
        Map properties = ApplicationProperties.cacheProp();
        return "LFU".equals(properties.get(ALGORITHM_KEY))
                ? new LFUCache<>((Integer) properties.get(CAPACITY_KEY))
                : new LRUCache<>((Integer) properties.get(CAPACITY_KEY));

    }
}
