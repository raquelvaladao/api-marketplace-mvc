package com.api.estudo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProdutoDTO {

    private String nome;
    private BigDecimal valor;
    private String nomeDono;
}
