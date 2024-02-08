package com.colosseo.global.config.audit;

import com.colosseo.global.config.security.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig implements AuditorAware<String> {
    private static final String DEFAULT = "system";

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<String> userName = SecurityUtils.getCurrentUsername();

        if (userName.isEmpty() || !authentication.isAuthenticated()) {
            return Optional.of(DEFAULT);
        }

//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.of(DEFAULT);
//        }

        return userName;
//        return Optional.of(DEFAULT);
    }
}
