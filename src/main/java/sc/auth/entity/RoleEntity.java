package sc.auth.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles", schema = "auth")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;
}
