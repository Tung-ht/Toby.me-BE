package tunght.toby.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tunght.toby.be.entity.AvailableTag;

import java.util.List;

public interface AvailableTagRepository extends JpaRepository<AvailableTag, Long> {
    List<AvailableTag> getAllByIsPinned(Integer isPinned);
}
