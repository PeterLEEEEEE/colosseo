//package com.colosseo.global.config.security;
//
//import com.colosseo.global.handler.ErrorResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import java.io.IOException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class LoginFailureHandler implements AuthenticationFailureHandler {
//
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        log.error("아이디 혹은 비밀번호가 올바르지 않습니다.");
//
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .status(400)
//                .message("아이디 혹은 비밀번호가 올바르지 않습니다.")
//                .code(HttpStatus.BAD_REQUEST.toString())
//                .build();
//
//
//        response.setStatus(HttpStatus.BAD_REQUEST.value());
//        objectMapper.writeValue(response.getWriter(), errorResponse);
//    }
//}
