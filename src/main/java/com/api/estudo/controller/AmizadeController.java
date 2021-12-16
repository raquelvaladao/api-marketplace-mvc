package com.api.estudo.controller;

import com.api.estudo.dto.mappers.AmizadeMapper;
import com.api.estudo.dto.mappers.UsuMapper;
import com.api.estudo.dto.response.ResponseAmigoDTO;
import com.api.estudo.dto.response.ResponsePedidoAmizadeDTO;
import com.api.estudo.dto.response.ResponseUsuarioDTO;
import com.api.estudo.entities.Amizade;
import com.api.estudo.exceptions.InputInvalidoException;
import com.api.estudo.services.AmizadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/v1/amizade")
@Api(tags = "Amizade")
public class AmizadeController {

    private final AmizadeMapper mapper;
    private final UsuMapper usuarioMapper;
    private final AmizadeService amizadeService;

    public AmizadeController(AmizadeMapper mapper, UsuMapper usuarioMapper, AmizadeService amizadeService) {
        this.mapper = mapper;
        this.usuarioMapper = usuarioMapper;
        this.amizadeService = amizadeService;
    }

    @PostMapping("/request/{id}")
    @ApiOperation(value = "Enviar pedido", nickname = "enviarPedido", response = ResponsePedidoAmizadeDTO.class)
    public ResponseEntity<ResponsePedidoAmizadeDTO> enviarPedido(@PathVariable(name = "id")Long destinatarioId){
        try {

            Amizade pedido = amizadeService.salvarPedido(destinatarioId);
            return ResponseEntity.ok(mapper.fromEntity(pedido));
        } catch (InputInvalidoException e) {
            throw new InputInvalidoException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/aceitar/{id}")
    @ApiOperation(value = "Aceitar pedido", nickname = "aceitarPedido", response = ResponseAmigoDTO.class)
    public ResponseEntity<List<ResponseAmigoDTO>> aceitarPedido(@PathVariable(name = "id") Long remetenteId){
        try {
            List<ResponseAmigoDTO> amigosAtuais = amizadeService
                    .aceitarAmizade(remetenteId)
                    .stream()
                    .map(usuarioMapper::toResponseAmigo)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(amigosAtuais);
        } catch (InputInvalidoException e) {
            throw new InputInvalidoException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/rejeitar/{id}")
    @ApiOperation(value = "Rejeitar pedido", nickname = "rejeitarPedido", response = String.class)
    public ResponseEntity<String> rejeitarPedido(@PathVariable(name = "id") Long remetenteId){
        try {
            Amizade pedido = amizadeService.salvarPedido(remetenteId);

            return ResponseEntity.ok().build();
        } catch (InputInvalidoException e) {
            throw new InputInvalidoException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/desfazer/{id}")
    @ApiOperation(value = "Desfazer amizade", nickname = "desfazerAmizade", response = String.class)
    public ResponseEntity<String> desfazerAmizade(@PathVariable(name = "id") Long remetenteId){
        try {
            Amizade pedido = amizadeService.salvarPedido(remetenteId);

            return ResponseEntity.ok().build();
        } catch (InputInvalidoException e) {
            throw new InputInvalidoException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


}
