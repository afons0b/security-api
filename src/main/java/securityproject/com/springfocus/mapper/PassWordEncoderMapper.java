package securityproject.com.springfocus.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import securityproject.com.springfocus.annotations.EncodeMapping;

@RequiredArgsConstructor
@Component
public class PassWordEncoderMapper {
    private final PasswordEncoder encoder;

    @EncodeMapping
    public String encode(String rawPassword){
        return rawPassword == null ? null: encoder.encode(rawPassword);
    }
}
