package tunght.gr2.domain.tag.repository;

import tunght.gr2.domain.tag.entity.ArticleTagRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<ArticleTagRelationEntity, Long> {
}
