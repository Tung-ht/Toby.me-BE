package tunght.toby.be.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tunght.toby.be.consts.EUserAction;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class UserDto {
    private String email;
    private String username;
    private String bio;
    private String image;
    private List<String> roles;

    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Registration {
        @NotBlank(message = "tên hiển thị không được để trống")
        @Pattern(regexp = "[\\w\\d]{1,30}", message = "tên hiển thị chỉ được bao gồm chữ cái hoặc kí tự số, ít nhất 1 kí tự số")
        private String username;

        @NotBlank(message = "email không được để trống")
        @Email(message = "email không đúng định dạng")
        private String email;

        @NotBlank(message = "mật khẩu không được để trống")
        @Size(min = 8, max = 32, message = "mật khẩu phải từ 8-32 kí tự")
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class RegistrationResponse {
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Login {
        @NotBlank(message = "email không được để trống")
        @Email(message = "email không đúng định dạng")
        private String email;

        @NotBlank(message = "mật khẩu không được để trống")
        @Size(min = 8, max = 32, message = "mật khẩu phải từ 8-32 kí tự")
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Update {
        private Long id;
        private String email;
        @NotBlank(message = "tên hiển thị không được để trống")
        @Pattern(regexp = "[\\w\\d]{1,30}", message = "tên hiển thị chỉ được bao gồm chữ cái hoặc kí tự số, ít nhất 1 kí tự số")
        private String username;
        private String bio;
        private String image;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class RequestOTP {
        private String email;
        private String otp;
    }
}
