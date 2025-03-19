package com.simpleAuth.api.persistence.service;

import com.simpleAuth.api.domain.models.UserModel;
import com.simpleAuth.api.persistence.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository ;



    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> searchFindByEmail =  userRepository.findByEmail(username);
        if(searchFindByEmail.isEmpty()){
            return  null;
        }

        UserModel userModel = searchFindByEmail.get();

        List<GrantedAuthority>authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority(""));


        return new User(
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.isEnabled(),
                userModel.isAccountNoExpired(),
                userModel.isCredentialNoExpired(),
                userModel.isAccountNoLocked(),
                authorityList);

    }
}
