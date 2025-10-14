package com.br.pdvfrontend.api.preco.dto;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
public record PrecoRequest (
        BigDecimal valor,
        Data dataAlteracao,
        Data horaAlteracao
){
}
