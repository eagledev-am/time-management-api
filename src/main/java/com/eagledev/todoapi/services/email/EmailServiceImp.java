package com.eagledev.todoapi.services.email;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailServiceImp implements EmailService {
    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void send(String to, String subject, String body) {
        try {
            logger.debug("<==Start to Send Email==>");
            logger.info("<==Recipient : {}==>" , to);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("magdyabdo484@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            mailSender.send(mimeMessage);
            logger.debug("<==Finish sending email==>");
        }
        catch (Exception e){
            logger.error("<=={}==>" ,e.getMessage());
            throw new MailSendException("Fail To Send Email !");
        }
    }
}
