package com.colosseo.controller;

import com.colosseo.model.article.Article;
import com.colosseo.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class}) // SpringExtension은 사실 필요없긴 함(@SpringBootTest를 쓰기 때문에)
public class ArticleControllerDocTest {
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;
    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("[GET] - 헬스체크용 API 테스트")
    public void getArticle() throws Exception {
        // given
        this.mockMvc.perform(get("/api/v1/health-check").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("index"));

        // when

        // then
    }

    @Test
    @DisplayName("[GET] - 아티클 단건 조회 API")
    public void getArticleById() throws Exception {
        // given
        Article article = Article.builder()
                .title("test1")
                .content("test1's title")
                .build();

//        articleRepository.save(article);
        // when

        // then
        this.mockMvc.perform(get("/api/v1/article/1").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("index"));
    }
}
