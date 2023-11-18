package com.colosseo.controller;

import com.colosseo.dto.article.ArticleRequest;
import com.colosseo.global.enums.RoleType;
import com.colosseo.model.article.Article;
import com.colosseo.repository.ArticleRepository;
import com.colosseo.repository.UserRepository;
import com.colosseo.securityConfig.CustomMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {
    String accessToken =
            "Bearer " +
            "eyJhbGciOiJIUzUxMiJ9.eyJhdXRoIjoiUk9MRV9VU0VSIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNjk5OTQ1Nzc1LCJleHAiOjE3MDAwMzIxNzV9.xAEkP6nyJQ9Tdaou1jwZYaN-M7g9QGZ_bLdl_No9ZNAlzhsZc-gVZl8fkXOulVXLUpcleU0yqO2efqI_ByTrEQ";
    /*
    Article 생성, 조회, 수정, 삭제
     */
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArticleRepository articleRepository;


    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clean() {
        articleRepository.deleteAll();
//        userRepository.deleteAll();
    }

    @Test
//    @WithMockUser(username = "admin@gmail.com ", roles = {"ADMIN"}, password = "abcdef12345!")
    @CustomMockUser(username = "test99@admin.com", roleType = RoleType.ADMIN)
    @DisplayName("[POST] 아티클 생성 API")
    public void postArticle() throws Exception {
        // given
//        User user = User.builder()
//                .email("test1@gmail.com")
//                .password("abcdef12345!")
//                .roleType(RoleType.ADMIN)
//                .build();
//        userRepository.save(user);

        ArticleRequest request = ArticleRequest.builder()
                .title("this is title")
                .content("hi")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/article")
//                        .param("")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
        // when
//        assertEquals(1L, articleRepository.count());
        Article article = articleRepository.findAll().get(0);
        // then

        assertEquals("this is title", article.getTitle());
        assertEquals("hi", article.getContent());
    }
}