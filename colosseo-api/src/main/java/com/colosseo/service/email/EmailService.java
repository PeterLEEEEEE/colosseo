package com.colosseo.service.email;

import com.colosseo.dto.email.CodeRequestDto;
import org.springframework.stereotype.Service;


public interface EmailService {
    String sendMessage(String to) throws Exception;
    Boolean verifyCode(CodeRequestDto codeRequestDto);
}
