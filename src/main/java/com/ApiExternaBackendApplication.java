package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients(basePackages = "com.infra.client")
@SpringBootApplication(scanBasePackages = "com")
@EnableJpaRepositories(basePackages = "com.repositories")
@EntityScan(basePackages = "com.domains")

public class ApiExternaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiExternaBackendApplication.class, args);
    }

}
