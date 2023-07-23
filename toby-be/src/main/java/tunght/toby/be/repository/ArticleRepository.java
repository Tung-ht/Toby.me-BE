package tunght.toby.be.repository;

import org.springframework.data.domain.Page;
import tunght.toby.be.entity.ArticleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    @EntityGraph("fetch-author-tagList")
    Optional<ArticleEntity> findBySlug(@Param("slug") String slug);

    @EntityGraph("fetch-author-tagList")
    @Query("SELECT DISTINCT a FROM ArticleEntity a LEFT JOIN FavoriteEntity f ON f.article.id = a.id " +
            "WHERE a.isApproved = :isApproved " +
            "AND a.author.id IN :ids " +
            "ORDER BY a.updatedAt DESC")
    Page<ArticleEntity> findByAuthorIdInOrderByCreatedAtDesc(@Param("ids") List<Long> ids, Integer isApproved, Pageable pageable);

    @EntityGraph("fetch-author-tagList")
    @Query("SELECT DISTINCT a FROM ArticleEntity a LEFT JOIN FavoriteEntity f ON f.article.id = a.id " +
            "WHERE a.isApproved = :isApproved " +
            "ORDER BY a.updatedAt DESC")
    Page<ArticleEntity> findListByPaging(Integer isApproved, Pageable pageable);

    @EntityGraph("fetch-author-tagList")
    @Query("SELECT DISTINCT a FROM ArticleEntity a LEFT JOIN FavoriteEntity f ON f.article.id = a.id " +
            "WHERE a.isApproved = :isApproved " +
            "AND a.author.username = :username " +
            "ORDER BY a.updatedAt DESC")
    Page<ArticleEntity> findByAuthorName(@Param("username") String username, Integer isApproved, Pageable pageable);

    @EntityGraph("fetch-author-tagList")
    @Query("SELECT DISTINCT a FROM ArticleEntity a JOIN ArticleTagRelationEntity t ON t.article.id = a.id LEFT JOIN FavoriteEntity f ON f.article.id = a.id " +
            "WHERE a.isApproved = :isApproved " +
            "AND t.tag = :tag " +
            "ORDER BY a.updatedAt DESC")
    Page<ArticleEntity> findByTag(@Param("tag") String tag, Integer isApproved, Pageable pageable);

    @EntityGraph("fetch-author-tagList")
    @Query("SELECT DISTINCT a FROM ArticleEntity a LEFT JOIN FavoriteEntity f ON f.article.id = a.id " +
            "WHERE f.user.username = :username AND a.isApproved = :isApproved " +
            "ORDER BY a.updatedAt DESC")
    Page<ArticleEntity> findByFavoritedUsername(@Param("username") String username, Integer isApproved, Pageable pageable);
}
