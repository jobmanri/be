package com.example.bak;

import com.example.bak.auth.infra.jwt.persistence.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class BakApplication implements CommandLineRunner {
    private final JwtProperties properties;
    public static void main(String[] args) {
        SpringApplication.run(BakApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("access: {}",properties.getAccess());

    }
}
