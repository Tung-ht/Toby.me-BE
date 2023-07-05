package tunght.toby.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tunght.toby.common.enums.ERole;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private ERole role;
}
