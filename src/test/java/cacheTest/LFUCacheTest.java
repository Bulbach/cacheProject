package cacheTest;

import by.alex.cache.impl.LFUCache;
import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LFUCacheTest {
    private LFUCache<String, Wagon> cache;

    @BeforeEach
    public void setUp() {
        cache = new LFUCache<>(3);
    }

    @Test
    public void testGetAndPut() {
        UUID uuid1 = UUID.fromString("feda712b-54b8-4e9e-ba67-fbc5665c3cab");
        UUID uuid2 = UUID.fromString("68c06a8c-6207-4b6a-812b-46ed1babc64d");
        UUID uuid3 = UUID.fromString("c64575b5-e896-4ee2-8c3e-37e349c314c8");

        Wagon wagon1 = new Wagon(uuid1, "W001", 100, 2020, LocalDate.now());
        Wagon wagon2 = new Wagon(uuid2, "W002", 150, 2019, LocalDate.now());
        Wagon wagon3 = new Wagon(uuid3, "W003", 200, 2018, LocalDate.now());

        cache.put(uuid1.toString(), wagon1);
        cache.put(uuid2.toString(), wagon2);
        cache.put(uuid3.toString(), wagon3);

        assertEquals(wagon1, cache.get(uuid1.toString()));
        assertEquals(wagon2, cache.get(uuid2.toString()));
        assertEquals(wagon3, cache.get(uuid3.toString()));
    }

    @Test
    public void testEviction() {
        UUID uuid1 = UUID.fromString("feda712b-54b8-4e9e-ba67-fbc5665c3cab");
        UUID uuid2 = UUID.fromString("68c06a8c-6207-4b6a-812b-46ed1babc64d");
        UUID uuid3 = UUID.fromString("c64575b5-e896-4ee2-8c3e-37e349c314c8");
        UUID uuid4 = UUID.fromString("b2596554-f405-4f4d-ade2-d7b462280321");

        Wagon wagon1 = new Wagon(uuid1, "W001", 100, 2020, LocalDate.now());
        Wagon wagon2 = new Wagon(uuid2, "W002", 150, 2019, LocalDate.now());
        Wagon wagon3 = new Wagon(uuid3, "W003", 200, 2018, LocalDate.now());
        Wagon wagon4 = new Wagon(uuid4, "W004", 250, 2017, LocalDate.now());

        cache.put(uuid1.toString(), wagon1);
        cache.put(uuid2.toString(), wagon2);
        cache.put(uuid3.toString(), wagon3);

        cache.get(uuid1.toString());
        cache.get(uuid1.toString());
        cache.get(uuid2.toString());
        cache.get(uuid2.toString());
        cache.put(uuid4.toString(), wagon4);

        assertNull(cache.get(uuid3.toString()));
        assertEquals(wagon1, cache.get(uuid1.toString()));
        assertEquals(wagon2, cache.get(uuid2.toString()));
        assertEquals(wagon4, cache.get(uuid4.toString()));
    }
}
