package com.br.pdvfrontend.api.pessoa.dto;

import com.br.pdvfrontend.enums.TipoPessoa;
import java.time.LocalDate;

/**
 * DTO (Record) usado para enviar dados ao Back-end (CREATE/UPDATE).
 */
public record PessoaRequest(
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa
) {}

