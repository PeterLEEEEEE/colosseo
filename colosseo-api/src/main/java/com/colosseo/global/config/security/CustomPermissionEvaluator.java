package com.colosseo.global.config.security;

import com.colosseo.exception.CustomException;
import com.colosseo.exception.ErrorCode;
import com.colosseo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final ArticleRepository articleRepository;
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        var article = articleRepository.findById((Long) targetId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_EXISTS));

        if(article.getUserId().equals(userPrincipal.getUserId())) {
            return true;
        }
        log.error("해당 사용자가 작성한 글이 아니므로 삭제 불가");
        return false;
    }
}
