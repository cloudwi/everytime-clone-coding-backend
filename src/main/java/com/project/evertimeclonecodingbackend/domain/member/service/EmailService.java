package com.project.evertimeclonecodingbackend.domain.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private String authNum;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private void createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 -> key.append((char) ((int) random.nextInt(26) + 97));
                case 1 -> key.append((char) ((int) random.nextInt(26) + 65));
                case 2 -> key.append(random.nextInt(9));
            }
        }

        authNum = key.toString();
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {
        createCode();
        String setFrom = "cloudwiiiii@gmail.com";
        String toEmail = email;
        String title = "EveryTime Clone Coding 회원가입 인증 번호";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, email);
        mimeMessage.setSubject(title);
        mimeMessage.setFrom(setFrom);
        mimeMessage.setText("인증 코드 : " + authNum);

        return mimeMessage;
    }

    public String sendEmail(String toEmail) throws MessagingException {
        MimeMessage mimeMessage = createEmailForm(toEmail);
        javaMailSender.send(mimeMessage);
        return authNum;
    }
}
