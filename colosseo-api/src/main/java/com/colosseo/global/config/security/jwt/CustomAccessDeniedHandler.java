package com.colosseo.global.config.security.jwt;

import com.colosseo.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler   implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        log.error("[403]");
        ErrorCode code = ErrorCode.NO_PERMISSION;

        JSONObject responseJson = new JSONObject();
        responseJson.put("status", code.getStatus());
        responseJson.put("code", code.getCode());
        responseJson.put("message", code.getMessage());

        response.getWriter().print(responseJson);
    }
}
