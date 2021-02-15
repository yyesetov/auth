package sc.auth.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cases", schema = "auth")
@Data
public class CasesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;
}
