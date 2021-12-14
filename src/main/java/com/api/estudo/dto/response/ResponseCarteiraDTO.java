package com.api.estudo.dto.response;

import com.api.estudo.enums.BandeiraCartao;
import com.api.estudo.enums.TipoMoeda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCarteiraDTO {
    private String codigo;
    private BigDecimal orcamento;
    private BandeiraCartao bandeira;
    private TipoMoeda moeda;
}
