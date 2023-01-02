package com.project.evertimeclonecodingbackend.global.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                // https://github.com/spring-projects/spring-boot/issues/11926
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.build();
    }
}
