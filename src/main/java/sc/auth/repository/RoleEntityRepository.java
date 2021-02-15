package sc.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sc.auth.entity.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByCode(String code);
}
