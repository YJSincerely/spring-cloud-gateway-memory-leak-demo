package com.example.demo.circuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreaker {
    @Bean
    public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(
            CircuitBreakerRegistry cbRegistry, TimeLimiterRegistry tlRegistry) {
        ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory = new ReactiveResilience4JCircuitBreakerFactory(cbRegistry,tlRegistry);

        reactiveResilience4JCircuitBreakerFactory.configure(resilience4JConfigBuilder -> {
            resilience4JConfigBuilder
                    .circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(99.9999f).minimumNumberOfCalls(Integer.MAX_VALUE).build())
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(100)).cancelRunningFuture(true).build());
        },"timeout");

        return reactiveResilience4JCircuitBreakerFactory;
    }
}
