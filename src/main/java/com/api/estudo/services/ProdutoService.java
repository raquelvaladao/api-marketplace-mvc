package com.api.estudo.services;


import com.api.estudo.entities.Produto;
import com.api.estudo.entities.Transacao;
import com.api.estudo.entities.Usuario;
import com.api.estudo.exceptions.EntityNotFoundException;
import com.api.estudo.repositories.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvarProduto(Produto entity) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entity.setUsuario(usuario);
        return produtoRepository.save(entity);
    }

    public Produto buscarProduto(Long id) {
        Optional<Produto> optional = produtoRepository.findById(id);
        if(optional.isEmpty()){
            throw new EntityNotFoundException("Produto não encontrado");
        }
        return optional.get();
    }

    public Page<Produto> listarTudo(Pageable pageable, Long id) {
        return produtoRepository.findAllByUsuario_Id(id, pageable);
    }


    public Produto deletarProduto(Long id){
        Produto aSerDeletado = buscarProduto(id);
        produtoRepository.delete(aSerDeletado);
        return aSerDeletado;
    }

    public void mudarProdutoDeDono(Produto produto, Usuario novoDono) {
        produto.setUsuario(novoDono);
        produtoRepository.save(produto);
    }
}
