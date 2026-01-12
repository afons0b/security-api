package securityproject.com.springfocus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import securityproject.com.springfocus.domain.User;
import securityproject.com.springfocus.repository.UserRepository;
import securityproject.com.springfocus.request.UserPostRequest;
import securityproject.com.springfocus.response.UserGetResponse;
import securityproject.com.springfocus.response.UserPostResponse;
import securityproject.com.springfocus.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/token-jwt")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService service;

    @PostMapping("/users")
    public ResponseEntity<UserPostResponse> save(@RequestBody UserPostRequest request){

        var response = service.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserGetResponse>> findAll(){

        var allUsers = service.findAll();

        return ResponseEntity.ok(allUsers);
    }
}
