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

    ArticleDto.MultipleArticle listArticle(final ArticleQueryParam articleQueryParam, Integer isApproved, final AuthUserDetails authUserDetails);

    void approveArticle(String slug);
}
