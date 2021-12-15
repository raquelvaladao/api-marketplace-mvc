package com.api.estudo.services;

import com.api.estudo.dto.request.RequestPutUsuarioDTO;
import com.api.estudo.dto.request.RequestUsuarioDTO;
import com.api.estudo.entities.Carteira;
import com.api.estudo.entities.Usuario;
import com.api.estudo.exceptions.EntityNotFoundException;
import com.api.estudo.repositories.CarteiraRepository;
import com.api.estudo.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, CarteiraRepository carteiraRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carteiraRepository = carteiraRepository;
    }

    public Usuario buscarUsuarioPorEmail(String email){
        Optional<Usuario> optional = usuarioRepository.findByEmail(email);
        if(optional.isEmpty()){
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        return optional.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        return buscarUsuarioPorEmail(username);
    }

    public Usuario buscarUsuarioPorId(Long idUsuario) {
        Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
        if(optional.isEmpty()){
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        return optional.get();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.getCarteira().forEach(carteira -> carteira.setUsuario(usuario)); //se tiver ManyToOne usuario
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }
    public void deletarUsuario(Long id) {
//        usuario.getCarteira().forEach(carteira -> carteira.setUsuario(usuario)); se tiver ManyToOne usuario
        usuarioRepository.flush();
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(RequestPutUsuarioDTO dto){
        Usuario entity = buscarUsuarioPorEmail(dto.getEmail());
        entity.setNome(dto.getNome());
        entity.setSenha(dto.getSenha());

        usuarioRepository.save(entity);
        return entity;
    }

    public Usuario salvarSaldo(Usuario usuario){
        Usuario entity = buscarUsuarioPorId(usuario.getId());
        entity.setSaldo(usuario.getSaldo());
        usuarioRepository.save(entity);
        return entity;
    }

    public Carteira validarCartao(Long cartaoId) {
        Optional<Carteira> carteira = carteiraRepository.findById(cartaoId);
        if(carteira.isEmpty() || carteira.get().getUsuario() == null){
            throw new EntityNotFoundException("Cartão ou usuário inválido");
        }
        //verificar se o dono do cartão é a origem da transação.
        return carteira.get();
    }

}
