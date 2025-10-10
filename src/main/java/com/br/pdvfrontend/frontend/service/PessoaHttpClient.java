package com.br.pdvfrontend.frontend.service;

import com.br.pdvfrontend.api.dto.PessoaRequest;
import com.br.pdvfrontend.api.dto.PessoaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * Cliente HTTP para realizar as operações CRUD na API REST de Pessoas.
 * O método .block() é usado para tornar as chamadas síncronas.
 */
@Service
public class PessoaHttpClient {

    private final WebClient webClient;

    public PessoaHttpClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PessoaResponse> listarTodos() {
        // GET /api/pessoas
        return Objects.requireNonNull(webClient.get()
                .retrieve()
                .bodyToFlux(PessoaResponse.class)
                .collectList()
                .block());
    }

    public PessoaResponse criarPessoa(PessoaRequest request) {
        // POST /api/pessoas
        return webClient.post()
                .body(Mono.just(request), PessoaRequest.class)
                .retrieve()
                .bodyToMono(PessoaResponse.class)
                .block();
    }

    public PessoaResponse atualizarPessoa(Long id, PessoaRequest request) {
        // PUT /api/pessoas/{id}
        return webClient.put()
                .uri("/{id}", id)
                .body(Mono.just(request), PessoaRequest.class)
                .retrieve()
                .bodyToMono(PessoaResponse.class)
                .block();
    }

    public void deletarPessoa(Long id) {
        // DELETE /api/pessoas/{id}
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}