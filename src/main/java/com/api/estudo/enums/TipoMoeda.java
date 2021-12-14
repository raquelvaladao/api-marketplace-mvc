package com.api.estudo.enums;

public enum TipoMoeda {
    BITCOIN("BTC"),
    ETHEREUM("ETH"),
    DOGECOIN("DOGE"),
    LITECOIN("LITE"),
    SHIBAINU("SHIB"),
    PANCAKESWAP("CAKE"),
    TEZOS("XTZ");

    private final String nome;

    TipoMoeda(String nome) {
        this.nome = nome;
    }
}
