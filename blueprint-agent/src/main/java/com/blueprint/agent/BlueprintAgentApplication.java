package com.blueprint.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlueprintAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlueprintAgentApplication.class, args);
    }

}