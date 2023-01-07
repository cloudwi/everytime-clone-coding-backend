package com.project.everytimeclonecodingbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
public class EverytimeCloneCodingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EverytimeCloneCodingBackendApplication.class, args);
    }

}
