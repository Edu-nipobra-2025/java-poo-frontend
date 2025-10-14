package com.br.pdvfrontend.api.produto.dto;

public record ProdutoResponse (
        String nome,
        String referencia,
        String fornecedor,
        String categoria,
        String marca)
{}
