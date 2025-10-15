package com.br.pdvfrontend.api.pessoa.dto;

import com.br.pdvfrontend.enums.TipoPessoa;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// Para Entrada
public record PessoaRequest(
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa
)
{ }