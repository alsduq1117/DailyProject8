package com.project8.project8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Project8Application {

    public static void main(String[] args) {
        SpringApplication.run(Project8Application.class, args);
    }

}
