package securityproject.com.springfocus.config;

import com.sun.security.auth.UnixNumericUserPrincipal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import securityproject.com.springfocus.domain.Role;
import securityproject.com.springfocus.domain.User;
import securityproject.com.springfocus.repository.RoleRepository;
import securityproject.com.springfocus.repository.UserRepository;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        if (roleAdmin == null){
            roleAdmin = new Role();
            roleAdmin.setName(Role.Values.ADMIN.name());
            roleRepository.save(roleAdmin);
            System.out.println("role admin not found or does not exist");
        }

        var userAdmin = userRepository.findByName("ADMIN");
        Role finalRoleAdmin = roleAdmin;
        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin already exist");
                },
                () -> {
                    var user = new User();
                    user.setName("ADMIN");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(finalRoleAdmin));
                    userRepository.save(user);
                    System.out.println("user admin created");
                }
        );

    }
}
