package com.api.estudo.dto.mappers;

import com.api.estudo.dto.response.ResponsePedidoAmizadeDTO;
import com.api.estudo.entities.Amizade;
import com.api.estudo.services.UsuarioService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mapstruct.ReportingPolicy.*;

@Mapper(unmappedSourcePolicy = WARN,
        unmappedTargetPolicy = WARN,
        componentModel = "spring",
        uses = UsuarioService.class)
public abstract class AmizadeMapper {


    @Autowired
    UsuarioService usuarioService;

    @Mapping(target = "remetenteNome", expression = "java(entity.getRemetente().getNome())")
    @Mapping(target = "destinatarioNome", expression = "java(entity.getDestinatario().getNome())")
    public abstract ResponsePedidoAmizadeDTO fromEntity(Amizade entity);
}
