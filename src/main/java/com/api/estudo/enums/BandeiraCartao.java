package com.api.estudo.enums;

public enum BandeiraCartao {

    METAMASK("Metamask"),
    ALPHA("AlphaWallet"),
    TRUST("Trust Wallet"),
    COINBASE("Coinbase Wallet");

    private final String nome;

    BandeiraCartao(String nome) {
        this.nome = nome;
    }
}
