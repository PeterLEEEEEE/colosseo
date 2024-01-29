package com.colosseo;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.global.enums.RoleType;
import com.colosseo.repository.ArticleRepository;
import com.colosseo.securityConfig.CustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BulkInsertTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @CustomMockUser(username = "dissgogo@naver.com", roleType = RoleType.USER)
    public void bulkInsert() {
        for (int i=0; i < 10; i++) {
            ArticleDto articleDto = ArticleDto.builder()
                    .title("test title" + i)
                    .content("test content" + i)
                    .build();
            articleRepository.save(articleDto.toEntity());
        }
    }
}
