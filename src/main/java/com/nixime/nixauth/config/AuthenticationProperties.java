package com.nixime.nixauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
public record AuthenticationProperties(String type) {
    
}