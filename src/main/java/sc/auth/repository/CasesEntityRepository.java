package sc.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sc.auth.entity.CasesEntity;

public interface CasesEntityRepository extends JpaRepository<CasesEntity, Long> {
    CasesEntity findByCode(String code);
}
