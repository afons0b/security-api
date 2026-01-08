package securityproject.com.springfocus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import securityproject.com.springfocus.domain.Role;
import securityproject.com.springfocus.domain.User;
import securityproject.com.springfocus.repository.RoleRepository;
import securityproject.com.springfocus.repository.UserRepository;
import securityproject.com.springfocus.response.UserPostResponse;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserPostResponse userPostResponse){
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var userFromdb = userRepository.findByName(userPostResponse.name());
        if (userFromdb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setName(userPostResponse.name());
        user.setPassword(passwordEncoder.encode(userPostResponse.password()));
        user.setRoles(Set.of(basicRole));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
