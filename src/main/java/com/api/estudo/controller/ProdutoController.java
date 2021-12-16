package com.api.estudo.controller;


import com.api.estudo.dto.mappers.ProdutoMapper;
import com.api.estudo.dto.request.RequestProdutoDTO;
import com.api.estudo.dto.response.ResponseProdutoDTO;
import com.api.estudo.entities.Produto;
import com.api.estudo.entities.Usuario;
import com.api.estudo.exceptions.EntityNotFoundException;
import com.api.estudo.exceptions.InputInvalidoException;
import com.api.estudo.services.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<ResponseProdutoDTO> registrarProduto(@Valid @RequestBody RequestProdutoDTO dto) {
        try {
            Produto toSave = produtoService.salvarProduto(mapper.fromDTO(dto));
            ResponseProdutoDTO response = mapper.fromEntity(toSave);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new InputInvalidoException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar produto", nickname = "consultarProduto", response = ResponseProdutoDTO.class)
    public ResponseEntity<ResponseProdutoDTO> consultarProduto(@PathVariable(name = "id") Long id) {
        try {
            Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(verSeUsuarioEstaHabilitado(usuarioLogado.getId())) {
                Produto produto = produtoService.buscarProduto(id);
                return ResponseEntity.ok(mapper.fromEntity(produto));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
    }

    @GetMapping("/todos/{id}")
    @ApiOperation(value = "Listar produtos", nickname = "listarProdutos", response = ResponseProdutoDTO.class)
    public ResponseEntity<Page<ResponseProdutoDTO>> listarProdutos(@PageableDefault Pageable pageable,
                                                                   @PathVariable(name = "id") Long id) {
        try {
            Page<ResponseProdutoDTO> response = produtoService
                    .listarTudo(pageable, id).map(mapper::fromEntity);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "Deletar produtos", nickname = "deletarProduto", response = ResponseProdutoDTO.class)
    public ResponseEntity<ResponseProdutoDTO> deletarProduto(@PathVariable(name = "id") Long id) {
        try {
            Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(verSeUsuarioEstaHabilitado(usuarioLogado.getId())) {
                Produto produtoDeletado = produtoService.deletarProduto(id);
                return ResponseEntity.ok(mapper.fromEntity(produtoDeletado));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
    }

    private boolean verSeUsuarioEstaHabilitado(Long idUsuarioPath){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.getPerfil().getNome().equals("ADMIN") || usuarioLogado.getId().equals(idUsuarioPath);
    }
}
