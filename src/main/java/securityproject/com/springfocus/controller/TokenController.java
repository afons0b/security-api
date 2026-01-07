package securityproject.com.springfocus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import securityproject.com.springfocus.repository.UserRepository;
import securityproject.com.springfocus.request.LoginRequest;
import securityproject.com.springfocus.response.LoginResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/token-jwt")
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var user = repository.findByUserName(loginRequest.username());

        if (user.isEmpty() || user.get().isLoginCorrect(loginRequest, passwordEncoder)){
            throw new BadCredentialsException("user or password is invalid");
        }


    }
}
