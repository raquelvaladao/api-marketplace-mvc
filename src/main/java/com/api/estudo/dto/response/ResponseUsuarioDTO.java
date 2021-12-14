package com.api.estudo.dto.response;

import com.api.estudo.dto.request.RequestCarteiraDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUsuarioDTO {
    private String email;
    private String nome;
    private List<ResponseCarteiraDTO> carteiraDTO;
}
