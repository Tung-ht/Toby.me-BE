package tunght.gr2.domain.article.servie;

import tunght.gr2.domain.article.dto.ArticleDto;
import tunght.gr2.domain.article.model.ArticleQueryParam;
import tunght.gr2.domain.article.model.FeedParams;
import tunght.gr2.security.AuthUserDetails;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(final ArticleDto article, final AuthUserDetails authUserDetails);

    ArticleDto getArticle(final String slug, final AuthUserDetails authUserDetails);

    ArticleDto updateArticle(final String slug, final ArticleDto.Update article, final AuthUserDetails authUserDetails);

    void deleteArticle(final String slug, final AuthUserDetails authUserDetails);

    List<ArticleDto> feedArticles(final AuthUserDetails authUserDetails, final FeedParams feedParams);

    ArticleDto favoriteArticle(final String slug, final AuthUserDetails authUserDetails);

    ArticleDto unfavoriteArticle(final String slug, final AuthUserDetails authUserDetails);

    List<ArticleDto> listArticle(final ArticleQueryParam articleQueryParam, final AuthUserDetails authUserDetails);
}
