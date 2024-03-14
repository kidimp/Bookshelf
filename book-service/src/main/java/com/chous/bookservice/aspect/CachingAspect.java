package com.chous.bookservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class CachingAspect {
    private final Map<String, Object> cache = new HashMap<>();

    @Around("@annotation(com.chous.bookservice.annotation.Cacheable)")
    public Object cacheMethodResult(ProceedingJoinPoint joinPoint) throws Throwable {
        String cacheKey = generateCacheKey(joinPoint);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        } else {
            Object result = joinPoint.proceed();
            cache.put(cacheKey, result);
            return result;
        }
    }

    private String generateCacheKey(ProceedingJoinPoint joinPoint) {
        StringBuilder keyBuilder = new StringBuilder(joinPoint.getSignature().toShortString());
        for (Object arg : joinPoint.getArgs()) {
            keyBuilder.append(arg.toString());
        }
        return keyBuilder.toString();
    }
}
