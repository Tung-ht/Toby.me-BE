package tunght.toby.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    DUPLICATED_USER("Thông tin người dùng đã tồn tại", HttpStatus.UNPROCESSABLE_ENTITY),
    LOGIN_INFO_INVALID("Thông tin đăng nhập không chính xác", HttpStatus.UNPROCESSABLE_ENTITY),
    ALREADY_FOLLOWED_USER("Người dùng này đã được theo dõi", HttpStatus.UNPROCESSABLE_ENTITY),
    ALREADY_FAVORITED_ARTICLE("Bài viết này đã được thêm vào yêu thích", HttpStatus.UNPROCESSABLE_ENTITY),
    OTP_INVALID("OTP không hợp lệ", HttpStatus.UNPROCESSABLE_ENTITY),

    USER_NOT_FOUND("Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("Role không tồn tại", HttpStatus.NOT_FOUND),
    FOLLOW_NOT_FOUND("Follow không tồn tại", HttpStatus.NOT_FOUND),
    ARTICLE_NOT_FOUND("Bài viết không tồn tại", HttpStatus.NOT_FOUND),
    FAVORITE_NOT_FOUND("Follow không tồn tại", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("Bình luận không tồn tại", HttpStatus.NOT_FOUND),

    ;

    private final String message;
    private final HttpStatus status;

    Error(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
