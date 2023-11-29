package com.colosseo.global.config.security;

import com.colosseo.exception.ErrorCode;
import com.colosseo.exception.CustomException;
import com.colosseo.model.user.User;
import com.colosseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        // cache needed

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_EXIST_OR_WRONG_PASSWORD)
        );

        return UserPrincipal.of(user);
    }
}
