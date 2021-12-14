package com.api.estudo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUsuarioDTO {

    @NotEmpty(message = "Nome errado")
    private String nome;

    @NotEmpty(message = "Email errado")
    @Email
    @Size(min=5, max = 20)
    private String email;


    private String senha;
    private Long perfilId;
    private List<RequestCarteiraDTO> requestCarteiraDTOS;
}
