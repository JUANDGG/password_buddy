package com.managerPasswords.api.domain.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.managerPasswords.api.domain.errors.CustomAuthenticationEntryPoint;
import com.managerPasswords.api.domain.errors.GlobalError;
import com.managerPasswords.api.domain.errors.GlobalErrorEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint ;

    public JwtFilter (JwtUtil jwtUtil ,CustomAuthenticationEntryPoint customAuthenticationEntryPoint){
        this.jwtUtil = jwtUtil;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint ;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenRequest = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            StringBuffer tokenCorrectFormat = getStringBuffer(tokenRequest);
            //validate token
            DecodedJWT decodedJWT = jwtUtil.validateAndDecodifiedToken(tokenCorrectFormat.toString());

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

            filterChain.doFilter(request, response);


        }catch (Exception e){
            SecurityContextHolder.clearContext();
            customAuthenticationEntryPoint.commence(request, response, new org.springframework.security.core.AuthenticationException(e.getMessage()) {});
        }

    }


    private static StringBuffer getStringBuffer(String tokenRequest) throws GlobalError {
        if(tokenRequest ==null || tokenRequest.isEmpty()){throw new GlobalError(GlobalErrorEnum.JWT_ISEMPTY.getMessage());}

        String tokenTrimSpaces = tokenRequest.trim();
        StringBuffer tokenCorrectFormat = new StringBuffer();


        //formater token
        if(tokenTrimSpaces.toLowerCase().startsWith("bearer ")){
            tokenCorrectFormat.append(tokenTrimSpaces.substring(7));
        }
        else{
            tokenCorrectFormat.append(tokenTrimSpaces);
        }
        return tokenCorrectFormat;
    }

}

