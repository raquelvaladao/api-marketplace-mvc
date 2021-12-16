package com.api.estudo.controller;


import com.api.estudo.dto.mappers.TransacaoMapper;
import com.api.estudo.dto.request.RequestTransacaoDTO;
import com.api.estudo.dto.response.ResponseProdutoDTO;
import com.api.estudo.dto.response.ResponseTransacaoDTO;
import com.api.estudo.dto.response.ResponseUsuarioDTO;
import com.api.estudo.entities.Transacao;
import com.api.estudo.exceptions.UsuarioNaoPermitido;
import com.api.estudo.services.TransacaoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
    @Api(tags = "Transação")
    @RequestMapping("/v1/transacoes")
    public class TransacaoController {

    private final TransacaoMapper mapper;
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoMapper mapper, TransacaoService transacaoService) {
        this.mapper = mapper;
        this.transacaoService = transacaoService;
    }

    @PostMapping
    @ApiOperation(value = "Realizar transação", nickname = "realizarTransacao", response = ResponseTransacaoDTO.class)
    public ResponseEntity<ResponseTransacaoDTO> realizarTransacao(@Valid @RequestBody RequestTransacaoDTO dto) {
        try {
            Transacao transacao = mapper.fromDTO(dto);
            transacaoService.processarTransacao(transacao, dto.getOrigemId());
            return ResponseEntity.ok(mapper.fromEntity(transacao));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Listar transações", nickname = "listarTransacoes", response = ResponseTransacaoDTO.class)
    public ResponseEntity<Page<ResponseTransacaoDTO>> listarTransacoes(@PageableDefault Pageable pageable,
                                                                 @RequestParam(name = "login") String login) {
        try {
            Page<ResponseTransacaoDTO> response = transacaoService
                    .listarTransacoes(pageable, login)
                    .map(mapper::fromEntity);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new UsuarioNaoPermitido("Usuário não pode ver o recurso");
        }
    }
}
