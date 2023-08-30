package com.colosseo.service.email;

import org.springframework.stereotype.Service;


public interface EmailService {
    String sendMessage(String to) throws Exception;
    Boolean verifyCode(String code);
}
