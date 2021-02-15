package sc.auth.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cases_roles", schema = "auth")
@Data
public class CasesRolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private CasesEntity casesEntity;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

}
