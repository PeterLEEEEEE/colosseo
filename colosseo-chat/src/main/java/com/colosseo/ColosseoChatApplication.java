package com.colosseo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ColosseoChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ColosseoChatApplication.class, args);
    }
}
