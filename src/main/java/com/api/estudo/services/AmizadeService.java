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
import java.util.Collection;
import java.util.List;

import static com.api.estudo.enums.StatusAmizade.*;

@Service
public class AmizadeService {

    public static final int INCREMENTA = 1;
    public static final int DECREMENTA = -1;


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

       public List<Usuario> responderPedidoAmizade(Long remetenteId, boolean aceitarAmizade) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario remetente = usuarioService.buscarUsuarioPorId(remetenteId);
        Amizade pedido = amizadeRepository
                .findByDestinatario_IdAndRemetente_Id(usuarioLogado.getId(), remetenteId);

        if(pedido != null && pedido.getStatus().equals(PENDENTE)){
            if(aceitarAmizade) {
                mudarQuantidadeAmigos(usuarioLogado, INCREMENTA);
                mudarQuantidadeAmigos(remetente, INCREMENTA);
                mudarStatusAmizade(pedido);
            } else
                amizadeRepository.delete(pedido);
            return getListaDeAmigos(usuarioLogado.getId());
        }
        throw new InputInvalidoException("Um pedido não pode ser aplicado ao proprio user");
    }

        private void mudarQuantidadeAmigos(Usuario usuario, int quantidade) {
        usuario.setQuantidadeAmigos(usuario.getQuantidadeAmigos() + quantidade);
        usuarioService.atualizarContagemAmigos(usuario);
    }


    public List<Usuario> getListaDeAmigos(Long usuarioId) {
        List<Usuario> amigos = new ArrayList<>();
        List<Amizade> amizades = amizadeRepository
                .findByRemetente_IdOrDestinatario_Id(usuarioId,usuarioId);

        amizades.stream()
                .filter(amizade -> amizade.getStatus().equals(ATIVO))
                .forEach(amizade -> {
                    if (amizade.getRemetente().getId().equals(usuarioId)) {
                        amigos.add(amizade.getDestinatario());
                    } else
                        amigos.add(amizade.getRemetente());
                });
        return amigos;
    }


    public List<Usuario> desfazerAmizade(Long remetenteId){
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario remetente = usuarioService.buscarUsuarioPorId(remetenteId);
        Amizade pedido = getAmizade(remetenteId, usuarioLogado);

        if(pedido != null && pedido.getStatus().equals(ATIVO)){
            mudarQuantidadeAmigos(usuarioLogado, DECREMENTA);
            mudarQuantidadeAmigos(remetente, DECREMENTA);
            amizadeRepository.delete(pedido);
        }
        return getListaDeAmigos(usuarioLogado.getId());
    }

    private Amizade getAmizade(Long remetenteId, Usuario usuarioLogado) {
        Amizade pedido = amizadeRepository
                .findByDestinatario_IdAndRemetente_Id(usuarioLogado.getId(), remetenteId);

        if(pedido == null) {
            pedido = amizadeRepository
                    .findByDestinatario_IdAndRemetente_Id(remetenteId, usuarioLogado.getId());
        }
        return pedido;
    }

    private void mudarStatusAmizade(Amizade pedido) {
        pedido.setStatus(ATIVO);
        amizadeRepository.save(pedido);
    }

    private void verSeRemetenteEhIgualDestinatario(Usuario usuarioLogado, Usuario destinatario) {
        if(destinatario.getEmail().equals(usuarioLogado.getEmail())){
            throw new InputInvalidoException("Esse usuário não pode enviar pra si mesmo");
        }
    }

    private void verificarSePedidoJaFoiFeito(Long remetenteId, Long destinatarioId) {
        if(amizadeRepository.findByRemetente_IdAndDestinatario_Id(remetenteId, destinatarioId) != null
                || amizadeRepository.findByRemetente_IdAndDestinatario_Id(destinatarioId, remetenteId) != null){
            throw new InputInvalidoException("Esse pedido já foi feito.");
        }
    }

    public List<Amizade> getPedidosPendentes(Long id) {
        //List<Amizade> pedidosPendentes = new ArrayList<>();
        List<Amizade> amizades = amizadeRepository
                .findByDestinatario_IdAndStatus(id, PENDENTE);
//        for(Amizade amizade : amizades){
//            if(amizade.getDestinatario().getId().equals(id)) {
//                pedidosPendentes.add(amizade);
//            }
//        }
        return amizades;
    }
}
