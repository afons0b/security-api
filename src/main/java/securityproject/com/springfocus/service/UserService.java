package securityproject.com.springfocus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import securityproject.com.springfocus.domain.Role;
import securityproject.com.springfocus.domain.User;
import securityproject.com.springfocus.mapper.UserMapper;
import securityproject.com.springfocus.repository.RoleRepository;
import securityproject.com.springfocus.repository.UserRepository;
import securityproject.com.springfocus.request.UserPostRequest;
import securityproject.com.springfocus.response.UserPostResponse;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    @Transactional
    public UserPostResponse save(UserPostRequest request){
        User user = mapper.toUser(request);

        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        if (basicRole == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }

        user.setRoles(Set.of(basicRole));
        userRepository.save(user);

        return mapper.toUserPostResponse(user);
    }
}
