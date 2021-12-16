package com.api.estudo.dto.response;

import com.api.estudo.enums.StatusAmizade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePedidoAmizadeDTO {

    private StatusAmizade status;
    private String remetenteNome;
    private String destinatarioNome;
}
