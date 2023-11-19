package by.alex.cache.impl;

import by.alex.cache.AbstractCache;
import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import by.alex.util.ApplicationProperties;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.UUID;

@UtilityClass
public class CacheFactory {

    private static final String CAPASITY_KEY = "max-size";
    private static final String ALGARITHM_KEY = "algorithm";


    public static AbstractCache<UUID, WagonDto> createCache() {
        Map properties = ApplicationProperties.cacheProp();
        return "LFU".equals(properties.get(ALGARITHM_KEY))
                ? new LFUCache<>((Integer) properties.get(CAPASITY_KEY))
                : new LRUCache<>((Integer) properties.get(CAPASITY_KEY));

    }
}
