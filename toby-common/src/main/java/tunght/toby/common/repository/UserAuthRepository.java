package tunght.toby.common.repository;

import tunght.toby.common.entity.UserEntity;

import java.util.Optional;

public interface UserAuthRepository {
    Optional<UserEntity> findByEmail(String email);
}
