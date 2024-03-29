package tunght.toby.be.service;

import tunght.toby.be.dto.CommentDto;
import tunght.toby.common.security.AuthUserDetails;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface CommentService {
    CommentDto addCommentsToAnArticle(final String slug, final CommentDto comment, final AuthUserDetails authUserDetails);

    void delete(final String slug, final Long commentId, final AuthUserDetails authUserDetails) throws AccessDeniedException;

    List<CommentDto> getCommentsBySlug(final String slug, final AuthUserDetails authUserDetails);

    CommentDto update(final String slug, final Long commentId, final CommentDto.Update newComment, final AuthUserDetails authUserDetails) throws AccessDeniedException;
}
