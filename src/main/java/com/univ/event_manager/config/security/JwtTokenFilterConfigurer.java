package com.univ.event_manager.config.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    public JwtTokenFilterConfigurer() {
//        this.;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
    }
}
