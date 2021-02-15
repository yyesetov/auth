package sc.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sc.auth.entity.CasesRolesEntity;

public interface CasesRolesEntityRepository extends JpaRepository<CasesRolesEntity, Long> {
    
}
