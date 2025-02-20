package com.slippery.geminiresearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("*")
                .allowedMethods("POST","GET")
                .allowedOrigins("http://localhost:5173/")
                .allowedHeaders("Content-Type","Authorization")
                .maxAge(3600);

    }

}

