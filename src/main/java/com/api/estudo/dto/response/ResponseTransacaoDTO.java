package com.api.estudo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTransacaoDTO {

        private String origemNome;
        private String destinoNome;
        private String token;
        private BigDecimal valor;
        private Date data;
}
