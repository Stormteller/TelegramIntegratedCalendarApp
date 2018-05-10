package com.univ.event_manager.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("security.jwt")
public class JwtSecurityConfigProperties {
    private String secretKey;

    private String headerName;

    private String headerStartsWith;

    private long expireIn;
}
