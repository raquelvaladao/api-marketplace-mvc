package com.api.estudo.dto.mappers;


import com.api.estudo.dto.request.RequestTransacaoDTO;
import com.api.estudo.dto.response.ResponseTransacaoDTO;
import com.api.estudo.entities.Transacao;
import com.api.estudo.repositories.ProdutoRepository;
import com.api.estudo.repositories.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        componentModel = "spring")
public abstract class TransacaoMapper {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Mapping(target = "origem", expression = "java(usuarioRepository.findById(dto.getOrigemId()).get())")
    @Mapping(target = "destino", expression = "java(produtoRepository.findById(dto.getProdutoId()).get().getUsuario())")
    @Mapping(target = "produto", expression = "java(produtoRepository.findById(dto.getProdutoId()).get())")
    @Mapping(target = "valor", expression = "java(produtoRepository.findById(dto.getProdutoId()).get().getValor())")
    public abstract Transacao fromDTO(RequestTransacaoDTO dto);

    @Mapping(target = "origemNome", expression = "java(transacao.getOrigem().getNome())")
    @Mapping(target = "destinoNome", expression = "java(transacao.getDestino().getNome())")
    public abstract ResponseTransacaoDTO fromEntity(Transacao transacao);

}
