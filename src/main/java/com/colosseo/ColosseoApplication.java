package com.colosseo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ColosseoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColosseoApplication.class, args);
    }

}
