package com.tsi.despesas.configs;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class RateLimiterConfiguration {

    @Bean
    public RateLimiter categoriaRateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(5)
                .limitRefreshPeriod(Duration.ofSeconds(20))
                .timeoutDuration(Duration.ofMillis(500))
                .build();

        return RateLimiter.of("categoriaRateLimiter", config);
    }

    @Bean
    public RateLimiter despesaRateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(10)
                .limitRefreshPeriod(Duration.ofSeconds(15))
                .timeoutDuration(Duration.ofMillis(500))
                .build();

        return RateLimiter.of("despesaRateLimiter", config);
    }
}