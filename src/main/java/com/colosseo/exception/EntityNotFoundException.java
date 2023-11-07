package com.colosseo.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends CustomException{
    private final ErrorCode errorCode;
    public EntityNotFoundException(ErrorCode e) {
        super(e);
        this.errorCode = e;
    }
}
