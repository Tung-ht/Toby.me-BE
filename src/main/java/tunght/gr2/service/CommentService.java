package tunght.gr2.service;

import tunght.gr2.dto.CommentDto;
import tunght.gr2.security.AuthUserDetails;

import java.util.List;

public interface CommentService {
    CommentDto addCommentsToAnArticle(final String slug, final CommentDto comment, final AuthUserDetails authUserDetails);

    void delete(final String slug, final Long commentId, final AuthUserDetails authUserDetails);

    List<CommentDto> getCommentsBySlug(final String slug, final AuthUserDetails authUserDetails);
}
