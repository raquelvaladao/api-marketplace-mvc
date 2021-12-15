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

    @Size(min = 4, max = 20)
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email não pode ser vazio")
    @Size(min=5, max = 20, message = "Tamanho mínimo é 5 e máximo 20")
    private String email;

    @NotEmpty(message = "Senha não pode ser vazia.")
    @Size(min = 6, max = 40)
    private String senha;

    @NotNull(message = "Usuário deve ter um perfil.")
    private Long perfilId;

    private List<RequestCarteiraDTO> requestCarteiraDTOS;
}
