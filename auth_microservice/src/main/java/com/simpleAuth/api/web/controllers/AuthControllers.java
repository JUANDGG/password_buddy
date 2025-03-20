package com.simpleAuth.api.web.controllers;

import com.simpleAuth.api.domain.pojo.AuthBodyRequest;
import com.simpleAuth.api.domain.pojo.AuthResponseDTO;
import com.simpleAuth.api.persistence.service.SignInService;
import com.simpleAuth.api.persistence.service.SignUpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllers {

    private final SignInService signInService ;
    private final SignUpService signUpService ;

    public AuthControllers (SignInService signInService ,SignUpService signUpService){
        this.signInService = signInService ;
        this.signUpService = signUpService;
    }


    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> signIn(@Valid @RequestBody AuthBodyRequest authBodyRequest) {
        return ResponseEntity.ok(signInService.login(authBodyRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDTO> signUp(@Valid @RequestBody AuthBodyRequest authBodyRequest) {
        return ResponseEntity.ok(signUpService.register(authBodyRequest));
    }
}
