package sc.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sc.auth.entity.CasesRolesEntity;

import java.util.List;

@Repository
public interface CasesRolesEntityRepository extends JpaRepository<CasesRolesEntity, Long> {
    @Query(value = "select cs.casesEntity.code \n" +
            "  from CasesRolesEntity cs\n" +
            " where cs.roleEntity.id = ?1")
    List<String> findAllCasesByRoleId(Long id);

}
