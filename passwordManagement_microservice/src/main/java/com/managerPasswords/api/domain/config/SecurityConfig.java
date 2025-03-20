package com.managerPasswords.api.domain.config;

import com.managerPasswords.api.domain.util.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter ;

    public SecurityConfig(JwtFilter jwtFilter ){
        this.jwtFilter = jwtFilter ;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .csrf(csrf ->csrf.disable())
                .httpBasic(http ->http.disable())
                .authorizeHttpRequests(http ->http
                        .requestMatchers(HttpMethod.GET,"/api/v1/my-passwords").hasAuthority("READ")
                        .requestMatchers(HttpMethod.POST,"/api/v1/my-passwords").hasAuthority("CREATE")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/my-passwords/{nameSite}").hasAuthority("UPDATE")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/my-passwords/{nameSite}").hasAuthority("DELETE")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
                .build();
    }



}
