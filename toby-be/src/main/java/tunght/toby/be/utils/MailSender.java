package tunght.toby.be.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tunght.toby.be.consts.EUserAction;
import tunght.toby.be.consts.MailConst;
import tunght.toby.be.repository.UserRepository;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.Error;
import tunght.toby.common.utils.DataUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
//@RequiredArgsConstructor
public class MailSender {
    @Value("${mail-sender.host}")
    private String host;
    @Value("${mail-sender.port}")
    private String port;
    @Value("${mail-sender.email}")
    private String email;
    @Value("${mail-sender.password}")
    private String password;
    @Value("${toby.otp.valid-time}")
    private Integer otpValidSecs;
    @Autowired
    private MailTemplate mailTemplate;
    @Autowired
    private UserRepository userRepository;

    public void sendMail(String sendTo, EUserAction action) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(sendTo)});
            message.setFrom(new InternetAddress(email));
            message.setSubject(action.emailSubject);
            mailTemplate.setMailTemplate(action.emailTemplate);

            var otp = DataUtils.generateOTP();
            message.setContent(mailTemplate.getContent(otp), MailConst.CONTENT_TYPE_TEXT_HTML);

            Transport.send(message);

            if (action == EUserAction.VERIFY_EMAIL) {
                var user = userRepository.findLastestCreatedAccountByMail(sendTo).orElseThrow(()-> new AppException(Error.USER_NOT_FOUND));
                user.setOtp(otp);
                userRepository.save(user);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}