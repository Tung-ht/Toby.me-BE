package tunght.toby.be.service;

import tunght.toby.be.dto.ArticleDto;
import tunght.toby.be.dto.model.ArticleQueryParam;
import tunght.toby.be.dto.model.FeedParams;
import tunght.toby.common.security.AuthUserDetails;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(final ArticleDto article, final AuthUserDetails authUserDetails);

    ArticleDto getArticle(final String slug, final AuthUserDetails authUserDetails);

    ArticleDto updateArticle(final String slug, final ArticleDto.Update article, final AuthUserDetails authUserDetails);

    void deleteArticle(final String slug, final AuthUserDetails authUserDetails);

    List<ArticleDto> feedArticles(final AuthUserDetails authUserDetails, final FeedParams feedParams);

    ArticleDto favoriteArticle(final String slug, final AuthUserDetails authUserDetails);

    ArticleDto unfavoriteArticle(final String slug, final AuthUserDetails authUserDetails);

    ArticleDto.MultipleArticle listPublicArticle(final ArticleQueryParam articleQueryParam, final AuthUserDetails authUserDetails);

    ArticleDto.MultipleArticle listUnapprovedArticle(final ArticleQueryParam articleQueryParam, final AuthUserDetails authUserDetails);

    void approveArticle(String slug);

    void pinArticle(String slug);

    void unpinArticle(String slug);

    ArticleDto.MultipleArticle search(ArticleDto.SearchRequest searchRequest, AuthUserDetails authUserDetails);

    String getSlugById(Long id);
}
