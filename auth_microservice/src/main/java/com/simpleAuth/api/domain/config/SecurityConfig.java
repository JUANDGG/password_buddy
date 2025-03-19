package com.simpleAuth.api.domain.config;

import com.simpleAuth.api.domain.config.providers.DaoProvider;
import com.simpleAuth.api.domain.util.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain ;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private final DaoProvider daoProvider ;
    private final JwtFilter jwtFilter;

    public SecurityConfig (DaoProvider daoProvider ,JwtFilter jwtFilter){
        this.daoProvider=daoProvider;
        this.jwtFilter=jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return  http.csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic->httpBasic.disable())
                .authorizeHttpRequests(ar ->ar
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().denyAll())
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }


}
