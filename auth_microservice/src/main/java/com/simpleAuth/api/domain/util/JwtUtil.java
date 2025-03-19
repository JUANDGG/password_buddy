package com.simpleAuth.api.domain.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.tokenGenerate.secretkey}")
    private String secretKey;

    @Value("${jwt.tokenGenerate.timeExpire}")
    private String timeExpire;

    @Value("${jwt.tokenGenerate.userBackendGenerate}")
    private String userBackend;


    //spring nos va a devolver un objecto de authenticacion si el usuario esta o no esta registrado
    public String createToken(Authentication authentication ){

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        String userName = authentication.getPrincipal().toString();

        String authorities =authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(this.userBackend)
                .withSubject(userName)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(timeExpire)))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);


    }

    public DecodedJWT validateAndDecodifiedToken (String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

            JWTVerifier verifier =  JWT.require(algorithm).withIssuer(this.userBackend).build();

            return verifier.verify(token) ;

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("token no valido");
        }
    }


    public String extractUserName (DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }


    public Claim getClaim (DecodedJWT decodedJWT , String claimName){
        return decodedJWT.getClaim(claimName);
    }



    public Map<String , Claim> getAllClaim (DecodedJWT decodedJWT){
        return decodedJWT.getClaims();

    }
}
