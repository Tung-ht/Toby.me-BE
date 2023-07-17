package tunght.toby.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tunght.toby.be.entity.AvailableTag;

import java.util.List;

@Repository
public interface AvailableTagRepository extends JpaRepository<AvailableTag, Long> {
    List<AvailableTag> getAllByIsPinned(Integer isPinned);
}
