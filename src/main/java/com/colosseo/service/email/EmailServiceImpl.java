package com.colosseo.service.email;

import com.colosseo.dto.email.CodeRequestDto;
import com.colosseo.exception.ErrorCode;
import com.colosseo.global.config.redis.RedisDao;
import com.colosseo.exception.CustomException;
import com.colosseo.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final UserRepository userRepository;
    private final JavaMailSender emailSender;
    private final RedisDao redisDao;

    private MimeMessage messageGenerator(String to) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        String key = keyGenerator(to);
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[Colosseo] 이메일 인증번호 안내");

        StringBuilder msg = new StringBuilder();

        msg.append("<div style='margin:20px;'>");
        msg.append("<h1> 안녕하세요 Colosseo 입니다. </h1>");
        msg.append("<br>");
        msg.append("<p>아래 코드를 복사해 입력해주세요<p>");
        msg.append("<br>");
        msg.append("<p>감사합니다.<p>");
        msg.append("<br>");
        msg.append("<div align='center' style='border:1px solid black; font-family:verdana';>");
        msg.append("<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>");
        msg.append("<div style='font-size:130%'>");
        msg.append("CODE : <strong>");
        msg.append(key+"</strong><div><br/> ");
        msg.append("</div>");

        message.setText(String.valueOf(msg), "utf-8", "html");
        message.setFrom(new InternetAddress("dissgo12@gmail.com", "Colosseo")); // sender Info

        return message;
    }
    @Override
    public String sendMessage(String to) throws Exception {

        MimeMessage message = messageGenerator(to);

        try {
            emailSender.send(message);
        } catch(MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return "Successfully Sent";
    }


    public String keyGenerator(String email) throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        int randNumber = random.nextInt(10000);
        String numToString = String.format("%04d", randNumber);

        redisDao.setValues(email, numToString, Duration.ofMinutes(4));
        return String.format("%04d", randNumber);
    }

    public Boolean verifyCode(CodeRequestDto codeRequestDto) {
        String grantedCode = redisDao.getValues(codeRequestDto.getEmail());
        if (grantedCode == null || grantedCode.isEmpty()) {
            throw new CustomException(ErrorCode.VERIFICATION_CODE_EXPIRED);
        }
        if (!codeRequestDto.getCode().equals(grantedCode)) {
            throw new CustomException(ErrorCode.VERIFICATION_CODE_NOT_SAME);
        } else {
            redisDao.deleteValues(codeRequestDto.getEmail());
        }

        return true;
    }
}
