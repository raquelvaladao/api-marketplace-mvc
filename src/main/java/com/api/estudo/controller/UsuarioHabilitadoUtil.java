package com.api.estudo.controller;

import com.api.estudo.entities.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class UsuarioHabilitadoUtil {

        private static boolean verSeUsuarioEstaHabilitado(Long idUsuarioPath){
            Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return usuarioLogado.getPerfil().getNome().equals("ADMIN")
                    || usuarioLogado.getId().equals(idUsuarioPath);
        }

}
