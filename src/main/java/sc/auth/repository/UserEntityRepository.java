package sc.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sc.auth.entity.UserEntity;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);

    List<UserEntity> findAllByLogin(String username);
}
