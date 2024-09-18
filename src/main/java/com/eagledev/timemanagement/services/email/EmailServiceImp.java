package com.eagledev.timemanagement.services.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImp implements EmailService {
    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Value("${app.email}")
    String EmailFrom;
    @Override
    public void send(String to, String subject, String body) {
        try {
            logger.debug("<==Start to Send Email==>");
            logger.info("<==Recipient : {}==>" , to);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(EmailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(mimeMessage);
            logger.debug("<==Finish sending email==>");
        }
        catch (Exception e){
            logger.error("<=={}==>" ,e.getMessage());
            throw new MailSendException("Fail To Send Email !");
        }
    }
}
