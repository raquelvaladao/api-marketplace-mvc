package com.api.estudo.dto.request;


import com.api.estudo.entities.Carteira;
import com.api.estudo.entities.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTransacaoDTO {

    @NotNull(message = "Deve haver um cartão")
    private Long origemId; //id do cartão do comprador

    @NotNull(message = "Deve haver um produto válido para compra.")
    private Long produtoId;

    @JsonIgnore
    private Date data = Calendar.getInstance().getTime();

}
