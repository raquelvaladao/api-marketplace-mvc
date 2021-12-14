package com.api.estudo.dto.request;


import com.api.estudo.enums.BandeiraCartao;
import com.api.estudo.enums.TipoMoeda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCarteiraDTO {

    private String codigo;
    private BigDecimal orcamento;
    private BandeiraCartao bandeira;
    private TipoMoeda moeda;
}
