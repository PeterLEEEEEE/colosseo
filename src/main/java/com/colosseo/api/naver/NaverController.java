package com.colosseo.api.naver;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/api/v1/article")
@Tag(name = "네이버 뉴스 API", description = "네이버 뉴스 스크랩 API")
public class NaverController {

    @GetMapping("/naver")
    public String getArticle() {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/news.json")
                .queryParam("query","윤석열")
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "date")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        log.info("uri : {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        // 헤더 추가 위해
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "3uo_IvrPRNr_lSPyqAIy")
                .header("X-Naver-Client-Secret", "LuzH2WByJH")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        String resultToStr = result.getBody();
        try{
            resultToStr = Objects.requireNonNull(resultToStr).replaceAll("\\\\", "");
            resultToStr = resultToStr.replaceAll("<b>", "");
            resultToStr = resultToStr.replaceAll("</b>", "");
            resultToStr = resultToStr.replaceAll("&quot;", "");
            resultToStr = resultToStr.replaceAll("&nbsp;", " ");
            resultToStr = resultToStr.replaceAll("&amp;", "&");

            return resultToStr;
        } catch (NullPointerException e) {
            return "No Data";
        }

    }
}
