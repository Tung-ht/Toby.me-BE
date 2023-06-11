package tunght.gr2.repository;

import tunght.gr2.entity.CommentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @EntityGraph("fetch-author")
    List<CommentEntity> findByArticleId(Long articleId);
}
