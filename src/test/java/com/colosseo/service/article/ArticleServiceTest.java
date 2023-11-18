package com.colosseo.service.article;

import com.colosseo.dto.article.ArticleDto;
import com.colosseo.dto.user.UserDto;
import com.colosseo.global.enums.ProviderType;
import com.colosseo.global.enums.RoleType;
import com.colosseo.model.article.Article;
import com.colosseo.model.user.User;
import com.colosseo.repository.ArticleRepository;
import com.colosseo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("[Service] 아티클 CRUD")
class ArticleServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
//    @AfterEach
    void clean() {
        articleRepository.deleteAll();
//        userRepository.deleteAll();
    }

//    @AfterEach
//    void cleanUser() {
//        userRepository.delete(user);
//    }

    @Test
    @DisplayName("[POST] 아티클 작성")
    void postArticle() {
        User user = User.builder()
                .email("test1@gmail.com")
                .nickname("test1")
                .password("abcdef12345!")
                .roleType(RoleType.ADMIN)
                .providerType(ProviderType.LOCAL)
                .build();

        userRepository.save(user);

        ArticleDto articleDto = ArticleDto.builder()
                .title("test1")
                .content("test1's content")
                .build();

        articleDto.toEntity();
        articleService.postArticle(articleDto);

        assertEquals(1L, articleRepository.count());
        Article article = articleRepository.findAll().get(0);
        // then

        assertEquals("test1", article.getTitle());
        assertEquals("test1's content", article.getContent());
    }
}