package tunght.toby.common.repository;

import tunght.toby.common.entity.RoleEntity;
import tunght.toby.common.enums.ERole;

import java.util.Optional;

public interface CommonRoleRepository {
    Optional<RoleEntity> findByRole(ERole role);
}
