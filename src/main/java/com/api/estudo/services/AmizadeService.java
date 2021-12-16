package com.api.estudo.services;


import com.api.estudo.entities.Amizade;
import com.api.estudo.entities.Usuario;
import com.api.estudo.enums.StatusAmizade;
import com.api.estudo.exceptions.InputInvalidoException;
import com.api.estudo.repositories.AmizadeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.api.estudo.enums.StatusAmizade.*;

@Service
public class AmizadeService {

    private final UsuarioService usuarioService;
    private final AmizadeRepository amizadeRepository;

    public AmizadeService(UsuarioService usuarioService, AmizadeRepository amizadeRepository) {
        this.usuarioService = usuarioService;
        this.amizadeRepository = amizadeRepository;
    }

    public Amizade salvarPedido(Long destinatarioId) throws InputInvalidoException {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario destinatario = usuarioService.buscarUsuarioPorId(destinatarioId);

        verSeRemetenteEhIgualDestinatario(usuarioLogado, destinatario);
        verificarSePedidoJaFoiFeito(usuarioLogado.getId(), destinatario.getId());
        Amizade amizade = new Amizade(
                null,
                usuarioLogado,
                destinatario,
                Calendar.getInstance().getTime(),
                PENDENTE);
        return amizadeRepository.save(amizade);
    }

    //so pode aceitar amizade se RECEBEU o pedido
    //se eu tiver um pedido em que o principal() é o destinatario(recebeu o pedido), posso aceitar.

    public List<Usuario> aceitarAmizade(Long remetenteId){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario remetente = usuarioService.buscarUsuarioPorId(remetenteId);
        Amizade pedido = amizadeRepository
                .findByDestinatario_IdAndRemetente_Id(usuarioLogado.getId(), remetenteId);

        if(pedido != null){
            usuarioLogado.setQuantidadeAmigos(usuarioLogado.getQuantidadeAmigos() + 1);
            remetente.setQuantidadeAmigos(remetente.getQuantidadeAmigos() + 1);
            usuarioService.atualizarAmigos(remetente);
            usuarioService.atualizarAmigos(usuarioLogado);
            mudarStatusAmizade(pedido, ATIVO);
        }

        return getListaDeAmigos(usuarioLogado.getId());
    }

    private List<Usuario> getListaDeAmigos(Long usuarioId) {
        List<Usuario> amigos = new ArrayList<>();
        List<Amizade> amizades = amizadeRepository
                .findByRemetente_IdOrDestinatario_IdAndStatus(usuarioId,usuarioId, ATIVO);
        for(Amizade amizade : amizades){
            if(amizade.getRemetente().getId().equals(usuarioId)){
                amigos.add(amizade.getDestinatario());
            } else{
                amigos.add(amizade.getRemetente());
            }
        }
        return amigos;
    }

    public List<Usuario> desfazerAmizade(Long remetenteId){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario remetente = usuarioService.buscarUsuarioPorId(remetenteId);
        Amizade pedido = amizadeRepository
                .findByDestinatario_IdAndRemetente_Id(usuarioLogado.getId(), remetenteId);

        if(pedido != null){
            usuarioLogado.setQuantidadeAmigos(usuarioLogado.getQuantidadeAmigos() + 1);
            remetente.setQuantidadeAmigos(remetente.getQuantidadeAmigos() + 1);
            usuarioService.atualizarAmigos(remetente);
            usuarioService.atualizarAmigos(usuarioLogado);
            mudarStatusAmizade(pedido, ATIVO);
        }
        return new ArrayList<>();
    }


    private void mudarStatusAmizade(Amizade pedido, StatusAmizade novoStatus) {
        pedido.setStatus(novoStatus);
        amizadeRepository.save(pedido);
    }

    private void verSeRemetenteEhIgualDestinatario(Usuario usuarioLogado, Usuario destinatario) {
        if(destinatario.getEmail().equals(usuarioLogado.getEmail())){
            throw new InputInvalidoException("Esse usuário não pode enviar esse input");
        }
    }

    private void verificarSePedidoJaFoiFeito(Long remetenteId, Long destinatarioId) {
        if(amizadeRepository.findByRemetente_IdAndDestinatario_Id(remetenteId, destinatarioId) != null
                || amizadeRepository.findByRemetente_IdAndDestinatario_Id(destinatarioId, remetenteId) != null){
            throw new InputInvalidoException("Esse pedido já foi feito.");
        }
    }

}
