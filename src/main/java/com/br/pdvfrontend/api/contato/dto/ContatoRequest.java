package com.br.pdvfrontend.api.contato.dto;

public record ContatoRequest (
    String telefone,
    String email,
    String endereco)
{}
