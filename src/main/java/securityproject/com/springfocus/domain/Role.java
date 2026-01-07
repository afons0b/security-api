package securityproject.com.springfocus.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public enum Values{
        BASIC(1L),
        ADMIN(2L);

        long id;

        Values(long id) {
            this.id = id;
        }
    }
}
