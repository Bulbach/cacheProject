package by.alex.aspect;

import by.alex.cache.AbstractCache;
import by.alex.dto.WagonDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Aspect
@Slf4j
public class CachingAspect {

    @Autowired
    private AbstractCache<UUID, WagonDto> cache;

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
            log.info("Founded wagon in repository");
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
        log.info("Created wagon " + createdWagonDto);
        cache.put(createdWagonDto.getId(), createdWagonDto);

        return createdWagonDto;
    }

    @Pointcut("@annotation(by.alex.annotation.CustomCachebleUpdate)")
    public void update() {
    }

    @Around(value = "update()")
    public Object cacheUpdate(ProceedingJoinPoint joinPoint) throws Throwable {

        WagonDto updated = (WagonDto) joinPoint.proceed();
        log.info("Updated wagon " + updated);
        cache.put(updated.getId(), updated);
        return updated;
    }

    @Pointcut("@annotation(by.alex.annotation.CustomCachebleDelete)")
    public void delete() {
    }

    @Around(value = "delete()")
    public Object cacheDelete(ProceedingJoinPoint joinPoint) throws Throwable {

        UUID id = (UUID) joinPoint.getArgs()[0];
        log.info("Deleted wagon with id = " + id);
        cache.delete(id);

        return joinPoint.proceed();
    }
}
