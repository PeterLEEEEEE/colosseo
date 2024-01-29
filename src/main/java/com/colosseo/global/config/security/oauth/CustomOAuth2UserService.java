package com.colosseo.global.config.security.oauth;

import com.colosseo.exception.ErrorCode;
import com.colosseo.global.config.security.UserPrincipal;
import com.colosseo.global.enums.AccountStatusType;
import com.colosseo.global.enums.ProviderType;
import com.colosseo.global.enums.RoleType;
import com.colosseo.exception.CustomException;
import com.colosseo.model.user.User;
import com.colosseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId='kakao'
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        ProviderType providerType = ProviderType.valueOf(registrationId.toUpperCase());

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());
        String providerEmail = oAuth2UserInfo.getEmail();
        Optional<User> user = userRepository.findByEmail(providerEmail);

        if (user.isPresent()) {
            return UserPrincipal.of(user.get());
//            if (user.get().getProviderType().equals(ProviderType.LOCAL)) {
//                user.get().setProviderType(ProviderType.valueOf(providerEmail));
//            }
//            if (!user.get().getProviderType().toString().equals(oAuth2UserInfo.getProvider())) {
//                throw new CustomException(ErrorCode.PROVIDER_ALREADY_EXIST);
//            }
        } else {
            return UserPrincipal.of(createUser(oAuth2UserInfo, providerType));
        }
//        User user = userRepository.findByEmail(providerEmail)
//                .orElseGet(() -> createUser(oAuth2UserInfo, providerType));

//        return UserPrincipal.of(user.get());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .providerType(providerType)
                .nickname(userInfo.getName())
                .roleType(RoleType.USER)
                .accountStatusType(AccountStatusType.ACTIVE)
                .build();

        return userRepository.save(user);
    }

}
