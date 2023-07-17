package tunght.toby.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tunght.toby.common.entity.RoleEntity;
import tunght.toby.common.enums.ERole;
import tunght.toby.common.repository.CommonRoleRepository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, CommonRoleRepository {
    @Override
    Optional<RoleEntity> findByRole(ERole role);
}
