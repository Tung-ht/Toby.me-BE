package tunght.toby.be.consts;

public enum EUserAction {
    RESET_PASSWORD("forgot-pw-otp", "Toby.me: Yêu cầu đổi mật khẩu"),
    VERIFY_EMAIL("registration-otp", "Toby.me: Xác nhận email đăng kí");

    public final String emailTemplate;
    public final String emailSubject;

    EUserAction(String emailTemplate, String emailSubject) {
        this.emailTemplate = emailTemplate;
        this.emailSubject = emailSubject;
    }
}
