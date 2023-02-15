package com.deloop.domain.cache.services;

import org.springframework.cache.CacheManager;

import java.util.Collection;

public interface CacheAdministration {
    void registerManager(String key, CacheManager cacheManager);

    void invalidateAll();

    void invalidate(String cacheName);

    void invalidate(String cacheManager, String cacheName);

    Collection<String> getCacheNames();
}
