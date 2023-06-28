package tunght.toby.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    DUPLICATED_USER("thông tin người dùng đã tồn tại", HttpStatus.UNPROCESSABLE_ENTITY),
    LOGIN_INFO_INVALID("thông tin đăng nhập không chính xác", HttpStatus.UNPROCESSABLE_ENTITY),
    ALREADY_FOLLOWED_USER("người dùng này đã được theo dõi", HttpStatus.UNPROCESSABLE_ENTITY),
    ALREADY_FAVORITED_ARTICLE("bài viết này đã được thêm vào yêu thích", HttpStatus.UNPROCESSABLE_ENTITY),
    OTP_INVALID("otp không hợp lệ", HttpStatus.UNPROCESSABLE_ENTITY),

    USER_NOT_FOUND("người dùng không tồn tại", HttpStatus.NOT_FOUND),
    FOLLOW_NOT_FOUND("follow không tồn tại", HttpStatus.NOT_FOUND),
    ARTICLE_NOT_FOUND("bài viết không tồn tại", HttpStatus.NOT_FOUND),
    FAVORITE_NOT_FOUND("follow không tồn tại", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("bình luận không tồn tại", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus status;

    Error(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
