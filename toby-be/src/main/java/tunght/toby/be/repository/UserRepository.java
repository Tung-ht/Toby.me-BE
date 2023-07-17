package tunght.toby.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.enums.EStatus;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // maybe there are several accounts with the same username, but only 1 account is activated
    List<UserEntity> findAllByUsernameAndStatus(String username, EStatus status);
}
