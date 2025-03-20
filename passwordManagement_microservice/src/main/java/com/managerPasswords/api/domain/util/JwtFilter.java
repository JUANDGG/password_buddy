package com.managerPasswords.api.domain.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;


    public JwtFilter (JwtUtil jwtUtil ){
        this.jwtUtil = jwtUtil;

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        String tokenRequest = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(tokenRequest != null){
            tokenRequest = tokenRequest.substring(7);

            //validate token
            DecodedJWT decodedJWT = jwtUtil.validateAndDecodifiedToken(tokenRequest);

            //extract claims
            String idUser = jwtUtil.getClaim(decodedJWT, "jti").asString();
            String userName = jwtUtil.extractUserName(decodedJWT);
            String authorities = jwtUtil.getClaim(decodedJWT, "authorities").asString();
            Collection<? extends GrantedAuthority> grantedAuthoritieList  = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);


            //register the objecto authentication
            SecurityContext context = SecurityContextHolder.getContext();
            CustomUserDetails customUserDetails = new CustomUserDetails(Long.parseLong(idUser),userName,grantedAuthoritieList);


            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,null,grantedAuthoritieList);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        }

        filterChain.doFilter(request, response);
    }
}
