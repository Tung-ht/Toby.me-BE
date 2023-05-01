package tunght.gr2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tunght.gr2.dto.ArticleDto;
import tunght.gr2.dto.CommentDto;
import tunght.gr2.dto.model.ArticleQueryParam;
import tunght.gr2.dto.model.FeedParams;
import tunght.gr2.service.ArticleService;
import tunght.gr2.service.CommentService;
import tunght.gr2.security.AuthUserDetails;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping
    public ArticleDto.SingleArticle<ArticleDto> createArticle(@Valid @RequestBody ArticleDto.SingleArticle<ArticleDto> article, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.createArticle(article.getArticle(), authUserDetails));
    }

    @GetMapping("/{slug}")
    public ArticleDto.SingleArticle<ArticleDto> getArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.getArticle(slug, authUserDetails));
    }

    @PutMapping("/{slug}")
    public ArticleDto.SingleArticle<ArticleDto> createArticle(@PathVariable String slug, @Valid @RequestBody ArticleDto.SingleArticle<ArticleDto.Update> article, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.updateArticle(slug, article.getArticle(), authUserDetails));
    }

    @DeleteMapping("/{slug}")
    public void deleteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        articleService.deleteArticle(slug, authUserDetails);
    }

    @GetMapping("/feed")
    public ArticleDto.MultipleArticle feedArticles(@ModelAttribute @Valid FeedParams feedParams, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        List<ArticleDto> dtos = articleService.feedArticles(authUserDetails, feedParams);
        return ArticleDto.MultipleArticle.builder().articles(dtos).articlesCount(dtos.size()).build();
    }

    @PostMapping("/{slug}/favorite")
    public ArticleDto.SingleArticle<ArticleDto> favoriteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.favoriteArticle(slug, authUserDetails));
    }

    @DeleteMapping("/{slug}/favorite")
    public ArticleDto.SingleArticle<ArticleDto> unfavoriteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ArticleDto.SingleArticle<>(articleService.unfavoriteArticle(slug, authUserDetails));
    }

    @GetMapping
    public ArticleDto.MultipleArticle listArticles(@ModelAttribute ArticleQueryParam articleQueryParam, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        List<ArticleDto> dtos = articleService.listArticle(articleQueryParam, authUserDetails);
        return ArticleDto.MultipleArticle.builder().articles(dtos).articlesCount(dtos.size()).build();
    }

    @PostMapping("/{slug}/comments")
    public CommentDto.SingleComment addCommentsToAnArticle(@PathVariable String slug, @RequestBody @Valid CommentDto.SingleComment comment, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return CommentDto.SingleComment.builder()
                .comment(commentService.addCommentsToAnArticle(slug, comment.getComment(), authUserDetails))
                .build();
    }

    @DeleteMapping("/{slug}/comments/{commentId}")
    public void deleteComment(@PathVariable("slug") String slug, @PathVariable("commentId") Long commentId, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        commentService.delete(slug, commentId, authUserDetails);
    }

    @GetMapping("/{slug}/comments")
    public CommentDto.MultipleComments getCommentsFromAnArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return CommentDto.MultipleComments.builder()
                .comments(commentService.getCommentsBySlug(slug, authUserDetails))
                .build();
    }
}
