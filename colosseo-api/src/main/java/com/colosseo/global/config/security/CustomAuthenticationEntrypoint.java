package com.colosseo.global.config.security;

import com.colosseo.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
//        log.error("Responding with unauthorized error. Message - {}", e.getMessage());
//
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        log.error("[401][인증] 권한이 필요합니다.");
        jsonResponseWrapper(request, response);
    }
    private void jsonResponseWrapper(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorCode code;
        JSONObject responseJson = new JSONObject();
        String exception = (String) request.getAttribute("jwt_exception");

        switch (exception != null ? exception : "NULL") {
            case "NULL" -> code = ErrorCode.JWT_COMMON_ERROR;
            case "TOKEN-0002" -> code = ErrorCode.JWT_ACCESS_TOKEN_NOT_FOUND;
            case "TOKEN-0003" -> code = ErrorCode.JWT_WRONG_SIGNATURE;
            case "TOKEN-0004" -> code = ErrorCode.JWT_UNSUPPORTED_TOKEN;
            case "TOKEN-0005" -> code = ErrorCode.JWT_EXPIRED_TOKEN;
            case "TOKEN-0006" -> code = ErrorCode.JWT_INVALID_TOKEN;
            default -> code = ErrorCode.UNKNOWN_ERROR;
        }

        responseJson.put("status", code.getStatus());
        responseJson.put("code", code.getCode());
        responseJson.put("message", code.getMessage());
        responseJson.put("path", request.getServletPath());
        response.getWriter().print(responseJson);
    }
}
