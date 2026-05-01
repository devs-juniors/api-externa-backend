package com.config;


import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {


    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            if (response.status() == 404) {
                return new RuntimeException("Recurso não encontrado na API externa");
            }
            if (response.status() == 429) {
                return new RuntimeException("Limite de requisições da API atingido");
            }
            if (response.status() == 500) {
                return new RuntimeException("Erro interno na API externa");
            }
            return new RuntimeException("Erro ao consultar API externa: " + response.status());
        };
    }
}
