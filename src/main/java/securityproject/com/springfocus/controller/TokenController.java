package securityproject.com.springfocus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import securityproject.com.springfocus.request.LoginRequest;
import securityproject.com.springfocus.response.LoginResponse;
import securityproject.com.springfocus.service.LoginService;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/token-jwt")
public class TokenController {

    private final LoginService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        var response = service.login(loginRequest);

        return ResponseEntity.ok(response);
    }
}
