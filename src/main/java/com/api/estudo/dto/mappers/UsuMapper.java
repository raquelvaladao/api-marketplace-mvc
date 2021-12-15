package com.api.estudo.dto.mappers;


import com.api.estudo.dto.request.RequestCarteiraDTO;
import com.api.estudo.dto.request.RequestProdutoDTO;
import com.api.estudo.dto.request.RequestPutUsuarioDTO;
import com.api.estudo.dto.request.RequestUsuarioDTO;
import com.api.estudo.dto.response.ResponseCarteiraDTO;
import com.api.estudo.dto.response.ResponseProdutoDTO;
import com.api.estudo.dto.response.ResponseUsuarioDTO;
import com.api.estudo.entities.Carteira;
import com.api.estudo.entities.Produto;
import com.api.estudo.entities.Usuario;
import com.api.estudo.repositories.PerfilRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        componentModel = "spring",
        uses = {PerfilRepository.class})
public abstract class UsuMapper {

    @Autowired
    PerfilRepository perfilRepository;
/*
    @Autowired
    BCryptPasswordEncoder encoder;*/

    //UsuMapper INSTANCE = Mappers.getMapper(UsuMapper.class);

    @Mapping(target = "carteiraDTO", source = "carteira")
    public abstract ResponseUsuarioDTO fromEntity(Usuario entity);

/*    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "carteira", source = "requestCarteiraDTOS")
    Usuario fromDTO(RequestUsuarioDTO dto);*/

    @Mapping(target = "saldo", constant = "0")
    @Mapping(target = "quantidadeAmigos", constant = "0")
    @Mapping(target = "carteira", source = "requestCarteiraDTOS")
    @Mapping(target = "perfil", expression = "java(perfilRepository.findById(dto.getPerfilId()).get())")
    public abstract Usuario fromDTO(RequestUsuarioDTO dto);

    @Mapping(target = "saldo", constant = "0")
    @Mapping(target = "quantidadeAmigos", constant = "0")
    //@Mapping(target = "perfil", expression = "java(perfilRepository.findById(dto.getPerfilId()).get())")
    public abstract Usuario fromDTO(RequestPutUsuarioDTO dto);


    public abstract ResponseCarteiraDTO fromEntity(Carteira carteira);

    public abstract Carteira fromDTO(RequestCarteiraDTO dto);

}
