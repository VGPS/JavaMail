package com.yourcompany.service;

import com.yourcompany.model.Email;
import com.yourcompany.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(Email email) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email.getReceiver().getEmail());
            helper.setSubject(email.getSubject());
            
            // Set CC if it's not null or empty
            if (email.getCc() != null && !email.getCc().isEmpty()) {
                helper.setCc(email.getCc().split(","));
            }
            
            String body = getEmailBody(email);
            helper.setText(body, true);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    private String getEmailBody(Email email) {
        switch (email.getType()) {
            case NOTIFICATION:
                return String.format(Constants.MessageTemplates.NOTIFICATION_TEMPLATE, 
                    email.getReceiver().getName(),
                    email.getSubject(),
                    email.getBody());
            case REMINDER:
                return String.format(Constants.MessageTemplates.REMINDER_TEMPLATE, 
                    email.getReceiver().getName(),
                    email.getSubject(),
                    email.getBody());
            case NEWSLETTER:
                return String.format(Constants.MessageTemplates.NEWSLETTER_TEMPLATE, 
                    email.getBody());
            case CUSTOM:
            default:
                return String.format(Constants.MessageTemplates.CUSTOM_TEMPLATE, 
                    email.getSubject(),
                    email.getBody());
        }
    }
}
