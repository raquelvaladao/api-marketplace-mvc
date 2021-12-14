package com.api.estudo.dto.mappers;


import com.api.estudo.dto.request.RequestProdutoDTO;
import com.api.estudo.dto.response.ResponseProdutoDTO;
import com.api.estudo.entities.Produto;
import com.api.estudo.repositories.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        componentModel = "spring",
        uses = UsuarioRepository.class)
public abstract class ProdutoMapper {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Mapping(target = "nomeDono", expression = "java(usuarioRepository.findById(produto.getUsuario().getId()).get().getNome())")
    public abstract ResponseProdutoDTO fromEntity(Produto produto);

    @Mapping(target = "usuario", expression = "java(usuarioRepository.findById(dto.getUsuarioId()).get())")
    public abstract Produto fromDTO(RequestProdutoDTO dto);
}
