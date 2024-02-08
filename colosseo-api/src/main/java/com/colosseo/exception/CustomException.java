package com.colosseo.exception;

import com.colosseo.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode e) {
        super(e.getMessage());
        this.errorCode = e;
    }
}
