package com.managerPasswords.api.domain.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails {

    private Long id;
    private String email;
    private  Collection<? extends GrantedAuthority> grantedAuthoritieList;

}
