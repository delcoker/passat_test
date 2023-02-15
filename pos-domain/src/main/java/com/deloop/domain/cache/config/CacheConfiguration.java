package com.deloop.domain.cache.config;

import com.deloop.domain.cache.CustomCacheErrorHandler;
import com.deloop.domain.cache.serializers.GenericRedisGzipSerializer;
import com.deloop.domain.cache.services.CacheAdministration;
import com.deloop.domain.cache.services.CacheAdministrationImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableCaching
@Slf4j
@Import(RedisConfiguration.class)
public class CacheConfiguration extends CachingConfigurerSupport {
    private static final int HOURS_TO_EXPIRE = 24;

    @Value("${env}")
    private String env;

    @Value("${cache.replicas}")
    private String cacheReplicas;

    @Value("${cache.server}")
    private String cacheServer;

    @Value("${cache.password}")
    private String cachePassword;

    @Value("${cache.enabled}")
    private boolean cacheEnabled;

    @Bean
    public RedisSettings redisSettings() {
        boolean sslEnabled = false;

        List<String> replicas = new ArrayList<>();
        if (StringUtils.isNotBlank(cacheReplicas)) {
            replicas = Arrays.asList(cacheReplicas.split(","));
        }

        return RedisSettings.builder()
                .server(cacheServer)
                .password(cachePassword)
                .sslEnabled(sslEnabled)
//                .replicas(replicas)
                .build();
    }

    @Bean
    public GenericRedisGzipSerializer genericRedisGzipSerializer(ObjectMapper objectMapper) {
        return new GenericRedisGzipSerializer(objectMapper);
    }

    @Bean
    @Primary
    public CacheManager cacheManager(CacheAdministration cacheAdministration,
                                     LettuceConnectionFactory lettuceConnectionFactory,
                                     GenericRedisGzipSerializer genericRedisGzipSerializer) {
        if (!cacheEnabled) {
            return new NoOpCacheManager();
        }

        lettuceConnectionFactory.afterPropertiesSet();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericRedisGzipSerializer))
                .entryTtl(Duration.ofHours(HOURS_TO_EXPIRE))
                .prefixCacheNameWith(env.toUpperCase() + "_");

        HashSet<String> initialCaches = new HashSet<>();
//        initialCaches.add("Employee"); // fails
        initialCaches.add("Employees");
        initialCaches.add("LoginEmployees");
        initialCaches.add("Item");
        initialCaches.add("Items");
        initialCaches.add("ItemCategory");
        initialCaches.add("ItemCategories");
//        initialCaches.add("UserDao"); // fails
        initialCaches.add("UserRole");

        CacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .disableCreateOnMissingCache()
                .initialCacheNames(initialCaches)
                .build();

        cacheAdministration.registerManager("remote1", cacheManager);
        return cacheManager;
    }

    @Bean
    CacheManager concurrentMapCacheManager(CacheAdministration cacheAdministration) {
        if (!cacheEnabled) {
            return new NoOpCacheManager();
        }

        CacheManager cacheManager = new ConcurrentMapCacheManager("ChartComputations");
        cacheAdministration.registerManager("local1", cacheManager);
        return cacheManager;
    }

    @Bean
    public CacheAdministration cacheAdminService() {
        return new CacheAdministrationImpl();
    }

    @Nullable
    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }
}
