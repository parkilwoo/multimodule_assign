package com.example.data_module.config;

import com.example.data_module.utils.Utils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;
import redis.embedded.exceptions.EmbeddedRedisException;

@Configuration
@Log4j2
public class EmbeddedRedisConfig {
    private final int port = 6379;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedisServer() {
        try {
            boolean isPortAvailable = Utils.isPortAvailable(port);
            if (!isPortAvailable) return;
            redisServer = RedisServer.builder()
                            .port(port)
                            .build();
            ;
            redisServer.start();
        }
        catch (EmbeddedRedisException exception) {
            log.warn("Failed Embedded Redis Connect");
        }
        catch (Exception e) {
            log.warn("{} 포트 사용중!", port);
        }

    }

    @PreDestroy
    public void stopRedisServer() {
        if(redisServer.isActive()) redisServer.stop();
    }
}