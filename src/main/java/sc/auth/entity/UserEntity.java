package sc.auth.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "auth")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;


    @Column
    private String fullname;

    @Column
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
}
