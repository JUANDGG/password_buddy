package com.simpleAuth.api.persistence.service;

import com.simpleAuth.api.domain.pojo.AuthBodyRequest;
import com.simpleAuth.api.domain.pojo.AuthResponseDTO;
import com.simpleAuth.api.domain.util.JwtUtil;
import com.simpleAuth.api.persistence.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignInService {

    private final UserDetailsServiceImpl userDetailService ;
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtil jwtUtil ;

    public SignInService(UserDetailsServiceImpl userDetailService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil =jwtUtil;
    }




    public AuthResponseDTO login(@NotBlank AuthBodyRequest authBodyRequest) {
        @Email(message = "the email is not valid")
        String email = authBodyRequest.email();
        @Size(min = 8, message = "the password must be at least 8 characters")
        String password = authBodyRequest.password();


        UserDetails userDetails = userDetailService.loadUserByUsername(email);

        //validate if the user not exist
        if (userDetails == null) {
            return new AuthResponseDTO(null, HttpStatus.BAD_REQUEST);
        }
        //validate the password is correct
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            return new AuthResponseDTO(null, HttpStatus.BAD_REQUEST);
        }

        String token =jwtUtil.createToken( new UsernamePasswordAuthenticationToken(email,null, userDetails.getAuthorities()));
        return new AuthResponseDTO(token, HttpStatus.ACCEPTED) ;
    }
}
