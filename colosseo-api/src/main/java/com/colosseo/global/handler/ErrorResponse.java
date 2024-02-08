package com.colosseo.global.handler;

import com.colosseo.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final Integer status;
    private final String message;
    private final String code;

    @Builder
    public ErrorResponse(Integer status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

    // @valid 관련 예외처리
    public static ResponseEntity<ErrorResponse> toResponseEntity(BindingResult bindingResult) {
        List<ObjectError> errorList = bindingResult.getAllErrors();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .code(errorList.get(0).getCode())
                        .message(errorList.get(0).getDefaultMessage())
                        .build()
                );
    }

    public static ResponseEntity<String> toResponseEntity(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
