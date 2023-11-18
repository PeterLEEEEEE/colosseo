package com.colosseo.securityConfig;

import com.colosseo.global.enums.ProviderType;
import com.colosseo.global.enums.RoleType;
import com.colosseo.model.user.User;
import com.colosseo.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockUserFactory implements WithSecurityContextFactory<CustomMockUser> {

    private final UserRepository userRepository;
    @Override
    public SecurityContext createSecurityContext(CustomMockUser annotation) {
        var user = User.builder()
                .email(annotation.username())
//                .name(annotation.name())
                .password(annotation.password())
                .providerType(ProviderType.LOCAL)
                .roleType(RoleType.ADMIN)
                .build();

        userRepository.save(user);

//        var principal = new UserPrincipal(user);

//        var role = new SimpleGrantedAuthority("ROLE_ADMIN");
//        var authenticationToken = new UsernamePasswordAuthenticationToken(principal,
//                user.getPassword(),
//                List.of(role));
        var authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(),
                user.getPassword());

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}
