package com.br.pdvfrontend.api.estoque.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueRequest(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataValidade,
        BigDecimal quantidade,
String localTanque,
        String localEndereco,
        String LoteFabricacao) {
}
