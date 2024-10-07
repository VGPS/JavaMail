package com.esrx.services.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// @EntityScan("com.esrx.services.common.entity")
// @EnableJpaRepositories("com.esrx.services.common.repository")
public class JavaMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaMailApplication.class, args);
    }
}
