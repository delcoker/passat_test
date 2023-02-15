package com.deloop.domain.cache.config;

import io.lettuce.core.ReadFrom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.clients.jedis.HostAndPort;

@Configuration
public class RedisConfiguration {

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory(RedisSettings redisSettings) {
        org.springframework.data.redis.connection.RedisConfiguration serverConfig;
        if (redisSettings.getReplicas().isEmpty()) {
            serverConfig = standaloneConfiguration(redisSettings);
        } else {
            serverConfig = masterReplicaConfiguration(redisSettings);
        }

        LettuceClientConfiguration lettuceClientConfiguration = lettuceClientConfiguration(redisSettings);

        return new LettuceConnectionFactory(serverConfig, lettuceClientConfiguration);
    }

    private org.springframework.data.redis.connection.RedisConfiguration standaloneConfiguration(RedisSettings redisSettings) {
        HostAndPort.setLocalhost("localhost");
        HostAndPort hostAndPort = HostAndPort.parseString(redisSettings.getServer());
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(hostAndPort.getHost(),
                hostAndPort.getPort());
        redisConfiguration.setPassword(redisSettings.getPassword());

        return redisConfiguration;
    }

    private org.springframework.data.redis.connection.RedisConfiguration masterReplicaConfiguration(RedisSettings redisSettings) {
        HostAndPort hostAndPort = HostAndPort.parseString(redisSettings.getServer());
        RedisStaticMasterReplicaConfiguration redisConfiguration = new RedisStaticMasterReplicaConfiguration(hostAndPort.getHost(), hostAndPort.getPort());
        redisConfiguration.setPassword(redisSettings.getPassword());

        redisSettings.getReplicas().forEach(item -> {
            HostAndPort hap = HostAndPort.parseString(item);
            redisConfiguration.addNode(hap.getHost(), hap.getPort());
        });

        return redisConfiguration;
    }

    private LettuceClientConfiguration lettuceClientConfiguration(RedisSettings redisSettings) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        if (redisSettings.isSslEnabled()) {
            builder.useSsl().disablePeerVerification();
        }

        builder.readFrom(ReadFrom.LOWEST_LATENCY);

        return builder.build();
    }
}
