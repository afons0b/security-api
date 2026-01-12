package securityproject.com.springfocus.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequest {

    @NotBlank(message = "campo nome é obrigatorio para criar um usuario")
    private String name;
    @NotBlank(message = "a senha é necessaria para o usuario")
    private String password;

}
