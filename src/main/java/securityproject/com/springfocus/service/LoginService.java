package securityproject.com.springfocus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import securityproject.com.springfocus.domain.Role;
import securityproject.com.springfocus.repository.UserRepository;
import securityproject.com.springfocus.request.LoginRequest;
import securityproject.com.springfocus.response.LoginResponse;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginResponse login(LoginRequest request){
        var user = userRepository.findByName(request.getName())
                .orElseThrow(
                        () -> new BadCredentialsException("Usuario ou senha invalidos"));

        var isPasswordValid = user.isLoginCorrect(request.getPassword(), passwordEncoder);

        if (!isPasswordValid){
            throw new BadCredentialsException("User or password invalid");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scope = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("sec-backend")
                .subject(user.getUuid().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .build();

        var jwtValue = jwtEncoder
                .encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);

    }
}
