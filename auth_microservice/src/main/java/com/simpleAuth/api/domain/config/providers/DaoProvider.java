package com.simpleAuth.api.domain.config.providers;

import com.simpleAuth.api.persistence.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;




@Component
public class DaoProvider {

    private final UserDetailsServiceImpl userDaoDetailsServiceImpl ;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final PasswordEncoder passwordEncoder ;

    public DaoProvider(UserDetailsServiceImpl userDaoDetailsServiceImpl ,PasswordEncoder passwordEncoder,DaoAuthenticationProvider daoAuthenticationProvider) {
        this.userDaoDetailsServiceImpl = userDaoDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder ;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }


    @Bean
    public AuthenticationProvider authenticationProvider (){
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDaoDetailsServiceImpl);
        return daoAuthenticationProvider;
    }
}
