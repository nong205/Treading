package com.Nong.treading.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    public void sendVerificationOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");

        String subject = "Verification OTP";
        String text = "Your verification OTP is: " + otp ;

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text,true);
        mimeMessageHelper.setTo(email);

        try {
            javaMailSender.send(mimeMessage);
        }catch (MailException e){
            throw new MessagingException(e.getMessage());
        }

    }

}
