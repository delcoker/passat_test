package com.deloop.domain.cache.services;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class CacheAdministrationImpl implements CacheAdministration {
    private final LinkedHashMap<String, CacheManager> cacheManagers = new LinkedHashMap<>();

    @Override
    public void registerManager(String key, CacheManager cacheManager) {
        cacheManagers.put(key, cacheManager);
    }

    @Override
    public void invalidateAll() {
        cacheManagers.forEach(((s, cacheManager) -> clearCaches(cacheManager)));
    }

    @Override
    public void invalidate(String cacheName) {
        cacheManagers.values().stream()
                .limit(1)
                .map(cacheManager -> cacheManager.getCache(cacheName))
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
    }

    @Override
    public void invalidate(String cacheManager, String cacheName) {
        if (!cacheManagers.containsKey(cacheManager)) {
            return;
        }

        Cache cache = cacheManagers.get(cacheManager).getCache(cacheName);

        if (cache == null) {
            return;
        }

        cache.clear();
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheManagers.values()
                .stream().limit(1)
                .map(CacheManager::getCacheNames)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private void clearCaches(CacheManager cacheManager) {
        cacheManager.getCacheNames().stream()
                .map(cacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
    }
}