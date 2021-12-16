package com.api.estudo.dto.mappers;

import com.api.estudo.dto.request.RequestCarteiraDTO;
import com.api.estudo.dto.response.ResponseCarteiraDTO;
import com.api.estudo.entities.Carteira;
import com.api.estudo.services.UsuarioService;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.WARN;


@Mapper(unmappedSourcePolicy = WARN,
        unmappedTargetPolicy = WARN,
        componentModel = "spring")
public abstract class CarteiraMapper {

    public abstract ResponseCarteiraDTO fromEntity(Carteira carteira);

    public abstract Carteira fromDTO(RequestCarteiraDTO dto);
}
