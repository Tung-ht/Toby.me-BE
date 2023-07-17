package tunght.toby.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.enums.EStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByUsernameAndStatus(String username, EStatus status);
    List<UserEntity> findAllByEmailAndStatus(String email, EStatus status);
    List<UserEntity> findAllByUsername(String username);
    List<UserEntity> findAllByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE email = :email " +
            "ORDER BY created_at DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<UserEntity> findLatestCreatedAccountByMail(String email);
}
