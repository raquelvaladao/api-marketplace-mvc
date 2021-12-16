package com.api.estudo.services;

import com.api.estudo.dto.request.RequestPutUsuarioDTO;
import com.api.estudo.dto.request.RequestUsuarioDTO;
import com.api.estudo.entities.Carteira;
import com.api.estudo.entities.Usuario;
import com.api.estudo.exceptions.EntityNotFoundException;
import com.api.estudo.exceptions.InputInvalidoException;
import com.api.estudo.repositories.CarteiraRepository;
import com.api.estudo.repositories.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Usuario buscarUsuarioPorId(Long idUsuario)  throws EntityNotFoundException {
        Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
        return optional.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Usuario salvarUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        usuario.getCarteira().forEach(carteira -> carteira.setUsuario(usuario)); //se tiver ManyToOne usuario
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        verSeEmailEstaUsado(usuario);
        return usuarioRepository.save(usuario);
    }

    private void verSeEmailEstaUsado(Usuario usuario) {
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new InputInvalidoException("Esse email está usado.");
        }
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.flush();
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(RequestPutUsuarioDTO dto){
        Usuario entity = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    public void atualizarAmigos(Usuario usuario){
        usuarioRepository.save(usuario);
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
