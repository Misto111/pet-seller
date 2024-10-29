package com.project.petSeller.service.impl;

import com.project.petSeller.service.EmailService;
import com.project.petSeller.service.aop.WarnIfExecutionExceeds;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String petsSellerEmail;

    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${mail.petsSeller") String petsSellerEmail) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.petsSellerEmail = petsSellerEmail;
    }

    @Override
    @WarnIfExecutionExceeds(timeInMillis = 600)
    public void sendRegistrationEmail(String userEmail, String userName, String activationCode) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(petsSellerEmail);
            mimeMessageHelper.setReplyTo(petsSellerEmail);
            mimeMessageHelper.setSubject("Welcome to pets-seller!");
            mimeMessageHelper.setText(generateRegistrationEmailBody(userName, activationCode), true);

            //javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateRegistrationEmailBody(String userName, String activationCode) {

        Context context = new Context();
        context.setVariable("username", userName);
        context.setVariable("activation_code", activationCode);

        return templateEngine.process("email/registration-email", context);

    }
}
