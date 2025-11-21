package com.example.bak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BakApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakApplication.class, args);
    }

}
