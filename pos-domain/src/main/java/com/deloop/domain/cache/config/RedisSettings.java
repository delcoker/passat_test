package com.deloop.domain.cache.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class RedisSettings {

    @Builder.Default
    private String server = "";

    @Builder.Default
    private String password = "";

    @Builder.Default
    private boolean sslEnabled = false;

    @Builder.Default
    private List<String> replicas = new ArrayList<>();
}
