package com.simpleAuth.api.persistence.service;
import com.simpleAuth.api.domain.models.UserModel;
import com.simpleAuth.api.domain.pojo.AuthBodyRequest;
import com.simpleAuth.api.domain.pojo.AuthResponseDTO;
import com.simpleAuth.api.domain.pojo.CustomUserDetails;
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
public class SignUpService {
    private final UserDetailsServiceImpl userDetailService ;
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtil jwtUtil ;

    public SignUpService(UserDetailsServiceImpl userDetailService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil =jwtUtil;
    }


    public AuthResponseDTO register(@NotBlank AuthBodyRequest authBodyRequest){
        @Email(message = "the email is not valid")
        String email = authBodyRequest.email();
        @Size(min = 8, message = "the password must be at least 8 characters")
        String password = authBodyRequest.password();


        CustomUserDetails customUserDetails =  (CustomUserDetails)userDetailService.loadUserByUsername(email);

        //validate if the user exist
        if (customUserDetails != null) {
            return new AuthResponseDTO(null, HttpStatus.BAD_REQUEST);
        }


        UserModel userModel = UserModel.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .build() ;

        //save in the memory and database
        CustomUserDetails user = userDetailService.createUserDetail(userModel);
        userRepository.save(userModel);

        System.out.print(user);

        //generate token
        String token =jwtUtil.createToken(userModel.getId(), new UsernamePasswordAuthenticationToken(email,null, user.getAuthorities()));

        //response token and state of the authentication
        return new AuthResponseDTO(token, HttpStatus.ACCEPTED) ;


    }


}
