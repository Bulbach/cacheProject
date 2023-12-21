package by.alex.aspect;

import by.alex.cache.AbstractCache;
import by.alex.cache.impl.CacheFactory;
import by.alex.dto.WagonDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Aspect
public class CachingAspect {

    private static final Logger logger = LoggerFactory.getLogger(CachingAspect.class);

    private AbstractCache<UUID, WagonDto> cache = CacheFactory.createCache();

    @Pointcut("@annotation(by.alex.annotation.CustomCachableGet)")
    public void getId() {
    }

    @Around(value = "getId()")
    public Object cacheWagon(ProceedingJoinPoint joinPoint) throws Throwable {

        UUID id = (UUID) joinPoint.getArgs()[0];

        if (cache.containsKey(id)) {
            return cache.get(id);
        } else {
            WagonDto wagon = (WagonDto) joinPoint.proceed();
            logger.info("Founded wagon in repository");
            cache.put(id, wagon);
            return wagon;
        }
    }

    @Pointcut("@annotation(by.alex.annotation.CustomCachebleCreate) ")
    public void create() {
    }

    @Around(value = "create()")
    public Object cacheCreate(ProceedingJoinPoint joinPoint) throws Throwable {

        WagonDto createdWagonDto = (WagonDto) joinPoint.proceed();
        logger.info("Created wagon " + createdWagonDto);
        cache.put(createdWagonDto.getId(), createdWagonDto);

        return createdWagonDto;
    }

    @Pointcut("@annotation(by.alex.annotation.CustomCachebleUpdate)")
    public void update() {
    }

    @Around(value = "update()")
    public Object cacheUpdate(ProceedingJoinPoint joinPoint) throws Throwable {

        WagonDto updated = (WagonDto) joinPoint.proceed();
        logger.info("Updated wagon " + updated);
        cache.put(updated.getId(), updated);
        return updated;
    }

    @Pointcut("@annotation(by.alex.annotation.CustomCachebleDelete)")
    public void delete() {
    }

    @Around(value = "delete()")
    public Object cacheDelete(ProceedingJoinPoint joinPoint) throws Throwable {

        UUID id = (UUID) joinPoint.getArgs()[0];
        logger.info("Deleted wagon with id = " + id);
        cache.delete(id);

        return joinPoint.proceed();
    }
}
