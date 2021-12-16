package com.api.estudo.services;


import com.api.estudo.entities.Carteira;
import com.api.estudo.entities.Transacao;
import com.api.estudo.entities.Usuario;
import com.api.estudo.repositories.CarteiraRepository;
import com.api.estudo.repositories.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class TransacaoService {

    public static final int ULTIMA_LETRA_ALFABETO = 26;
    public static final int DIGITO_MAXIMO = 9;

    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;
    private final TransacaoRepository transacaoRepository;
    private final CarteiraRepository carteiraRepository;

    public TransacaoService(UsuarioService usuarioService, TransacaoRepository transacaoRepository, ProdutoService produtoService, CarteiraRepository carteiraRepository) {
        this.usuarioService = usuarioService;
        this.transacaoRepository = transacaoRepository;
        this.produtoService = produtoService;
        this.carteiraRepository = carteiraRepository;
    }

    public Transacao processarTransacao(Transacao transacao, Long cartaoId){

        StringBuilder token = new StringBuilder();
        Carteira cartao = usuarioService.validarCartao(cartaoId);


        gerarRandomToken(token);
        mudarSaldo(cartao,
                transacao.getDestino().getId(),
                transacao.getProduto().getValor());
                transacao.setToken(token.toString());
        produtoService.mudarProdutoDeDono(transacao.getProduto(), transacao.getOrigem());
        return transacaoRepository.save(transacao);
    }

    private void gerarRandomToken(StringBuilder token) {
        int[] ints = new Random().ints(1, 9).limit(4).toArray();
        for (int anInt : ints) {
            token.append(anInt);
            Random letra = new Random();
            token.append((char)(letra.nextInt(ULTIMA_LETRA_ALFABETO) + 'a'));
            token.append(new Random().nextInt(DIGITO_MAXIMO));
            token.append((char)(letra.nextInt(ULTIMA_LETRA_ALFABETO) + 'a'));
            token.append("-");
            token.append(new Random().nextInt(DIGITO_MAXIMO));
        }
    }

    public void mudarSaldo(Carteira cartaoOrigem, Long destinoId, BigDecimal valor){
        Usuario destino = usuarioService.buscarUsuarioPorId(destinoId);

        cartaoOrigem.setOrcamento(cartaoOrigem.getOrcamento().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));
        usuarioService.salvarSaldo(destino);
        carteiraRepository.save(cartaoOrigem);
    }

    public Page<Transacao> listarTransacoes(Pageable pageable, String login){

        return transacaoRepository.findByOrigem_NomeOrDestino_Nome(login, login, pageable);
    }


}
