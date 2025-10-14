package com.br.pdvfrontend.api.custo.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CustoRequest(
        Double imposto,
        Double custoVariavel,
        Double custoFixo,
        Double margemLucro,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataProcessamento)
{ }