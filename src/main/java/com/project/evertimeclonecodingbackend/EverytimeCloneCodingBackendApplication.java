package com.project.evertimeclonecodingbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EverytimeCloneCodingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EverytimeCloneCodingBackendApplication.class, args);
    }

}
