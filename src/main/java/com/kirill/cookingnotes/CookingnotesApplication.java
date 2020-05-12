package com.kirill.cookingnotes;

import com.kirill.cookingnotes.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CookingnotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookingnotesApplication.class, args);
    }

}
