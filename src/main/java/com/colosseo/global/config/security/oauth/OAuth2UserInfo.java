package com.colosseo.global.config.security.oauth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    String getId() {
        return null;
    }

    String getName() {
        return null;
    }

    String getEmail() {
//        if (attributes.get("account_email").toString().isEmpty()) {
//            throw
//        }
//        return (String) attributes.get("account_email");
        return null;
    }

    String getUsername() {
        return null;
    }

    String getPassword() {
        return UUID.randomUUID().toString();
    }

    String getImageUrl() {
        return null;
    }

    String getProvider() {
        return null;
    }

    List<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
