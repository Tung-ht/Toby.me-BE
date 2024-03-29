package tunght.toby.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tunght.toby.be.dto.ArticleDto;
import tunght.toby.be.dto.CommentDto;
import tunght.toby.be.dto.model.ArticleQueryParam;
import tunght.toby.be.dto.model.FeedParams;
import tunght.toby.be.service.ArticleService;
import tunght.toby.be.service.CommentService;
import tunght.toby.common.security.AuthUserDetails;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "422", description = "Failed, unknown error"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
})
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

    @Operation(summary = "Api author updates posts")
    @PutMapping("/{slug}")
    public ArticleDto.SingleArticle<ArticleDto> updateArticle(@PathVariable String slug, @Valid @RequestBody ArticleDto.SingleArticle<ArticleDto.Update> article, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
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

    @Operation(summary = "These posts any user can see")
    @GetMapping
    public ArticleDto.MultipleArticle listApprovedArticles(@ModelAttribute ArticleQueryParam articleQueryParam, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return articleService.listPublicArticle(articleQueryParam, authUserDetails);
    }

    @Operation(summary = "These posts only admins and its author can see, and if they are approved, other users can see")
    @GetMapping("/unapproved")
    public ArticleDto.MultipleArticle listUnapprovedArticles(@ModelAttribute ArticleQueryParam articleQueryParam, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return articleService.listUnapprovedArticle(articleQueryParam, authUserDetails);
    }

    @PostMapping("/{slug}/comments")
    public CommentDto.SingleComment addCommentsToAnArticle(@PathVariable String slug, @RequestBody @Valid CommentDto.SingleComment comment, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return CommentDto.SingleComment.builder()
                .comment(commentService.addCommentsToAnArticle(slug, comment.getComment(), authUserDetails))
                .build();
    }

    @Operation(summary = "admin, postAuthor, commentAuthor can delete this comment")
    @DeleteMapping("/{slug}/comments/{commentId}")
    public void deleteComment(@PathVariable("slug") String slug, @PathVariable("commentId") Long commentId, @AuthenticationPrincipal AuthUserDetails authUserDetails) throws AccessDeniedException {
        commentService.delete(slug, commentId, authUserDetails);
    }

    @GetMapping("/{slug}/comments")
    public CommentDto.MultipleComments getCommentsFromAnArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return CommentDto.MultipleComments.builder()
                .comments(commentService.getCommentsBySlug(slug, authUserDetails))
                .build();
    }

    @PutMapping("/approve/{slug}")
    public void approveArticle(@PathVariable String slug) {
        articleService.approveArticle(slug);
    }

    @PutMapping("/{slug}/comments/{commentId}")
    public void updateComment(@PathVariable("slug") String slug,
                              @PathVariable("commentId") Long commentId,
                              @RequestBody CommentDto.Update newComment,
                              @AuthenticationPrincipal AuthUserDetails authUserDetails) throws AccessDeniedException {
        commentService.update(slug, commentId, newComment, authUserDetails);
    }

    @Operation(summary = "Admin pin a post")
    @PutMapping("/pin/{slug}")
    public void pinArticle(@PathVariable String slug) {
        articleService.pinArticle(slug);
    }

    @Operation(summary = "Admin unpin a post")
    @PutMapping("/unpin/{slug}")
    public void unpinArticle(@PathVariable String slug) {
        articleService.unpinArticle(slug);
    }

    @GetMapping("/search")
    public ArticleDto.MultipleArticle search(@ModelAttribute @Valid ArticleDto.SearchRequest searchRequest, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return articleService.search(searchRequest, authUserDetails);
    }

    @Operation(summary = "Get slug by id")
    @GetMapping("/slug")
    public String getSlugById(@RequestParam Long id) {
        return articleService.getSlugById(id);
    }
}
