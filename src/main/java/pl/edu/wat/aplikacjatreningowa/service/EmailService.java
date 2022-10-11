package pl.edu.wat.aplikacjatreningowa.service;
import javassist.Loader;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.edu.wat.aplikacjatreningowa.models.ConfirmationPasswordToken;
import pl.edu.wat.aplikacjatreningowa.models.ConfirmationToken;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService{

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    private final JavaMailSender mailSender;


    @Async
    public void sendEmail(UserAccount userAccount, ConfirmationToken confirmationToken, String text, String title)
    {
        try {
            String url = "http://localhost:8081/rejestracja/potwierdzenie?token=";
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(text
                    +url+confirmationToken.getToken());
            helper.setTo(userAccount.getEmail());
            helper.setSubject(title);
            helper.setFrom("aplikacjatreningowa@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }

    }

    @Async
    public void sendEmail(UserAccount userAccount, ConfirmationPasswordToken confirmationPasswordToken, String text, String title)
    {
        try {
            String url = "http://localhost:8081/konto/potwierdzenie-hasla&token=";
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(text
                    +url+confirmationPasswordToken.getToken());
            helper.setTo(userAccount.getEmail());
            helper.setSubject(title);
            helper.setFrom("aplikacjatreningowa@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }

    }

}
