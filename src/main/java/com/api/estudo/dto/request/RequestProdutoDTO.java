package com.api.estudo.dto.request;


import com.api.estudo.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestProdutoDTO {

    @NotEmpty(message = "Nome não pode ser vazio.")
    @Size(min = 4, max = 20)
    private String nome;

    @NotBlank(message = "Valor não pode ser vazio")
    private BigDecimal valor;

    @NotBlank(message = "ID do dono do produto não pode ser vazio")
    private Long usuarioId;
}
