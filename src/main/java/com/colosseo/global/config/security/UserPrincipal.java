package com.colosseo.global.config.security;

import com.colosseo.dto.user.UserDto;
import com.colosseo.global.enums.RoleType;
import com.colosseo.model.user.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {
    private final Long userId;
    private String username;
    private String password;
    private String email;
    private String nickname;
//    AccountStatusType accountStatusType;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserPrincipal(Long userId, String username, String password, String email, String nickname, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return "Peter";
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

    @Override
    public String getName() {
        return email;
    }

    public static UserPrincipal of(User user) {
        List<RoleType> roleTypes = List.of(user.getRoleType());
        return UserPrincipal.builder()
//                .username(user.getUsername())
                .userId(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .authorities(roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .toList())
                .build();
    }

    public UserDto toDto() {
        return UserDto.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
