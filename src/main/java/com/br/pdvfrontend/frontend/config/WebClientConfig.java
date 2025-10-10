package com.br.pdvfrontend.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuração do WebClient para se comunicar com a API REST do Back-end.
 */
@Configuration
public class WebClientConfig {

    // IMPORTANTE: Ajuste a URL/PORTA para onde sua API Back-end está rodando.
       private static final String BASE_URL = "http://localhost:8080/domain/pessoas";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }
}