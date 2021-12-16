package com.api.estudo.controller;


import com.api.estudo.dto.mappers.UsuMapper;
import com.api.estudo.dto.request.RequestPutUsuarioDTO;
import com.api.estudo.dto.request.RequestUsuarioDTO;
import com.api.estudo.dto.response.ResponseAmigoDTO;
import com.api.estudo.dto.response.ResponseCarteiraDTO;
import com.api.estudo.dto.response.ResponseUsuarioDTO;
import com.api.estudo.entities.Usuario;
import com.api.estudo.exceptions.EntityNotFoundException;
import com.api.estudo.exceptions.InputInvalidoException;
import com.api.estudo.exceptions.UsuarioNaoPermitido;
import com.api.estudo.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "Usuário")
@RequestMapping("/v1/usuario")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuMapper mapper;

    public UsuarioController(UsuarioService usuarioService, UsuMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Cadastrar usuário", nickname = "registrarUsuario", response = ResponseUsuarioDTO.class)
    public ResponseEntity<ResponseUsuarioDTO> registrarUsuario(@Valid @RequestBody RequestUsuarioDTO dto) {

        try{
            Usuario toSave = usuarioService.salvarUsuario(mapper.fromDTO(dto));
            ResponseUsuarioDTO response = mapper.fromEntity(toSave);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            throw new InputInvalidoException(e.getMessage());
        }
    }

    @GetMapping
    @ApiOperation(value = "Ver todos os usuários", nickname = "verCarteira", response = ResponseAmigoDTO.class)
    public ResponseEntity<Page<ResponseAmigoDTO>> listarTodos(@PageableDefault Pageable pageable) {

        try {
            Page<ResponseAmigoDTO> usuarios = usuarioService.listarTodos(pageable).map(mapper::toResponseAmigo);
            return ResponseEntity.ok(usuarios);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar usuário", nickname = "consultarUsuario", response = ResponseAmigoDTO.class)
    public ResponseEntity<ResponseAmigoDTO> consultarUsuario(@PathVariable(name = "id")Long id) {
        try {
                Usuario usuario = usuarioService.buscarUsuarioPorId(id);
                return ResponseEntity.ok(mapper.toResponseAmigo(usuario));
        } catch (Exception e) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletar usuário", nickname = "deletarUsuario", response = ResponseUsuarioDTO.class)
    public ResponseEntity<ResponseUsuarioDTO> deletarUsuario(@PathVariable(name = "id")Long id) {
        try {
            Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(usuarioLogado.getPerfil().getNome().equals("ADMIN")) {
                ResponseUsuarioDTO usuarioDTO = mapper.fromEntity(usuarioService.buscarUsuarioPorId(id));
                usuarioService.deletarUsuario(id);
                return ResponseEntity.ok(usuarioDTO);
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/editar")
    @ApiOperation(value = "Atualizar usuário", nickname = "atualizarUsuario", response = ResponseUsuarioDTO.class)
    public ResponseEntity<ResponseUsuarioDTO> atualizarUsuario(@Valid @RequestBody RequestPutUsuarioDTO dto) {

                ResponseUsuarioDTO response = mapper.fromEntity(usuarioService.atualizar(dto));
                return ResponseEntity.ok(response);

    }

    @GetMapping("/cart")
    @ApiOperation(value = "Ver carteira do usuário", nickname = "verCarteira", response = ResponseCarteiraDTO.class)
    public ResponseEntity<List<ResponseCarteiraDTO>> verCarteira() {

        try {
            Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ResponseUsuarioDTO usuarioDTO = mapper.fromEntity(usuarioService.buscarUsuarioPorId(usuarioLogado.getId()));
            return ResponseEntity.ok(usuarioDTO.getCarteiraDTO());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


}
