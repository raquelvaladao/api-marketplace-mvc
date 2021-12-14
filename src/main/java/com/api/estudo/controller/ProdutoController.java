package com.api.estudo.controller;


import com.api.estudo.dto.mappers.ProdutoMapper;
import com.api.estudo.dto.request.RequestProdutoDTO;
import com.api.estudo.dto.response.ResponseProdutoDTO;
import com.api.estudo.dto.response.ResponseUsuarioDTO;
import com.api.estudo.entities.Produto;
import com.api.estudo.exceptions.EntityNotFoundException;
import com.api.estudo.services.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Produto")
@RequestMapping("/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper mapper;

    public ProdutoController(ProdutoService produtoService, ProdutoMapper mapper) {
        this.produtoService = produtoService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Cadastrar produto", nickname = "registrarProduto", response = ResponseProdutoDTO.class)
    public ResponseEntity<ResponseProdutoDTO> registrarProduto(@RequestBody RequestProdutoDTO dto) {
        try {
            Produto toSave = produtoService.salvarProduto(mapper.fromDTO(dto));
            ResponseProdutoDTO response = mapper.fromEntity(toSave);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar produto", nickname = "consultarProduto", response = ResponseProdutoDTO.class)
    public ResponseEntity<ResponseProdutoDTO> consultarProduto(@PathVariable(name = "id") Long id) {
        try {
            Produto produto = produtoService.buscarProduto(id);
            return ResponseEntity.ok(mapper.fromEntity(produto));
        } catch (Exception e) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Listar produtos", nickname = "listarProdutos", response = ResponseProdutoDTO.class)
    public ResponseEntity<List<ResponseProdutoDTO>> listarProdutos(@PageableDefault Pageable pageable) {
        try {
            List<ResponseProdutoDTO> response = produtoService
                    .listarTudo(pageable)
                    .stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "Deletar produtos", nickname = "deletarProduto", response = ResponseProdutoDTO.class)
    public ResponseEntity<ResponseProdutoDTO> deletarProduto(@PathVariable(name = "id") Long id) {
        try {
            produtoService.deletarProduto(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Produto não encontrado");
        }
    }
}
