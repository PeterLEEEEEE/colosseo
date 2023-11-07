package com.colosseo.global.handler;

public class OAuthProviderMissMatchException extends RuntimeException   {
    public OAuthProviderMissMatchException(String message) {
        super(message);
    }
}
