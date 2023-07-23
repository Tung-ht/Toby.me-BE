package tunght.toby.be.repository;

import org.springframework.data.jpa.repository.Query;
import tunght.toby.be.entity.ArticleTagRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<ArticleTagRelationEntity, Long> {
    @Query(value =
            "SELECT tmp.tag FROM " +
            "(SELECT t.tag, COUNT(*) AS tag_count " +
            "FROM tags t INNER JOIN articles a ON t.article_id = a.id " +
            "WHERE a.is_approved = :isApproved " +
            "GROUP BY t.tag " +
            "ORDER BY tag_count DESC, t.tag ASC) as tmp", nativeQuery = true)
    List<String> getTagsByPopularity(Integer isApproved);

    List<ArticleTagRelationEntity> findAllByTag(String tag);
}
