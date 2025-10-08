package com.br.pdvfrontend.api.pessoa.dto;

import com.br.pdvfrontend.enums.TipoPessoa;
import java.time.LocalDate;

/**
 * DTO (Record) usado para receber dados do Back-end (READ).
 */
public record PessoaResponse(
        Long id, // O ID é necessário no Front-end para Edição/Exclusão
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa
) {}