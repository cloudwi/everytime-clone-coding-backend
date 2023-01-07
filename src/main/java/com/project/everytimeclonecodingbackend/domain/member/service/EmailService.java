package com.project.everytimeclonecodingbackend.domain.member.service;

import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import com.project.everytimeclonecodingbackend.domain.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class EmailService {
    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;
    // 타임리프를사용하기 위한 객체를 의존성 주입으로 가져온다
    private String authNum; //랜덤 인증 코드
    private final MemberRepository memberRepository;

    public EmailService(JavaMailSender emailSender, MemberRepository memberRepository) {
        this.emailSender = emailSender;
        this.memberRepository = memberRepository;
    }

    //랜덤 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    //메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        createCode(); //인증 코드 생성
        String setFrom = "cloudwiiiii@gmail.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "every-time-clone-web";
        String content = "every-time-clone-web 인증코드 : " + authNum; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //보낼 이메일 설정
        message.setSubject(title);
        message.setText(content);//제목 설정
        message.setFrom(setFrom); //보내는 이메일

        return message;
    }

    //실제 메일 전송
    @Transactional
    public String sendEmail(String toEmail, Authentication authentication) throws MessagingException, UnsupportedEncodingException {
        Member member = (Member) authentication.getPrincipal();
        member = memberRepository.findById(member.getId()).get();
        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        member.setEmailAuthenticationCode(authNum);
        //실제 메일 전송
        emailSender.send(emailForm);

        return authNum; //인증 코드 반환
    }
}
