package com.example.quests.config;

import com.example.quests.aspects.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {
    @Bean
    public LoggingAspect aspect() {
        return new LoggingAspect();
    }
}
