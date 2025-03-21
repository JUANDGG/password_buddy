package com.simpleAuth.api.domain.config.providers;

import com.simpleAuth.api.persistence.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Configuration
public class DaoProvider {

    private final UserDetailsServiceImpl userDaoDetailsServiceImpl ;


    public DaoProvider(UserDetailsServiceImpl userDaoDetailsServiceImpl ) {
        this.userDaoDetailsServiceImpl = userDaoDetailsServiceImpl;
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider (PasswordEncoder bcryptPasswordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bcryptPasswordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDaoDetailsServiceImpl);
        return daoAuthenticationProvider;
    }
}
