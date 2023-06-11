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
            "(SELECT tag, COUNT(*) AS tag_count " +
            "FROM tags " +
            "GROUP BY tag " +
            "ORDER BY tag_count DESC) as tmp", nativeQuery = true)
    List<String> getTagsByPopularity();
}
