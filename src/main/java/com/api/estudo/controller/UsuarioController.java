package com.api.estudo.controller;


import com.api.estudo.dto.mappers.UsuMapper;
import com.api.estudo.dto.request.RequestUsuarioDTO;
import com.api.estudo.dto.response.ResponseCarteiraDTO;
import com.api.estudo.dto.response.ResponseUsuarioDTO;
import com.api.estudo.entities.Usuario;
import com.api.estudo.exceptions.EntityNotFoundException;
import com.api.estudo.exceptions.InputInvalido;
import com.api.estudo.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
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
            throw new InputInvalido(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar usuário", nickname = "consultarUsuario", response = ResponseUsuarioDTO.class)
    public ResponseEntity<ResponseUsuarioDTO> consultarUsuario(@PathVariable(name = "id")Long id) {
        try {

            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(mapper.fromEntity(usuario));
        } catch (Exception e) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletar usuário", nickname = "deletarUsuario", response = ResponseUsuarioDTO.class)
    public ResponseEntity<ResponseUsuarioDTO> deletarUsuario(@PathVariable(name = "id")Long id) {
        try {
            ResponseUsuarioDTO usuarioDTO = mapper.fromEntity(usuarioService.buscarUsuarioPorId(id));
            usuarioService.deletarUsuario(id);
            return ResponseEntity.ok(usuarioDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar usuário", nickname = "atualizarUsuario", response = ResponseUsuarioDTO.class)
    public ResponseEntity<ResponseUsuarioDTO> atualizarUsuario(@RequestBody RequestUsuarioDTO dto,
                                                               @PathVariable(name = "id")Long id) {
            ResponseUsuarioDTO response = mapper.fromEntity(usuarioService.atualizar(dto));
            return ResponseEntity.ok(response);
    }

    @GetMapping("/cart/{id}")
    @ApiOperation(value = "Ver carteira do usuário", nickname = "verCarteira", response = ResponseCarteiraDTO.class)
    public ResponseEntity<List<ResponseCarteiraDTO>> verCarteira(@PathVariable(name = "id") Long id) {

        try {
            ResponseUsuarioDTO usuarioDTO = mapper.fromEntity(usuarioService.buscarUsuarioPorId(id));
            return ResponseEntity.ok(usuarioDTO.getCarteiraDTO());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
