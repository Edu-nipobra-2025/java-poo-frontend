package com.br.pdvfrontend.api.pessoa.dto;

import java.time.LocalDate;

// Para resposta
public record PessoaResponse(
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento
){}