package com.br.pdvfrontend.api.contato.dto;

public record ContatoResponse (
        String telefone,
        String email,
        String endereco)
{}