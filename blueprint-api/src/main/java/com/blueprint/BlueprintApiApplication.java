package com.blueprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlueprintApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlueprintApiApplication.class, args);
    }

}