package com.api.estudo.entities;

import com.api.estudo.enums.BandeiraCartao;
import com.api.estudo.enums.TipoMoeda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_carteira")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private BigDecimal orcamento;

    @Enumerated(value = EnumType.STRING)
    private BandeiraCartao bandeira;

    @Enumerated(value = EnumType.STRING)
    private TipoMoeda moeda;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    public Carteira(Long id, String codigo, BigDecimal orcamento, BandeiraCartao bandeira, TipoMoeda moeda) {
        this.id = id;
        this.codigo = codigo;
        this.orcamento = orcamento;
        this.bandeira = bandeira;
        this.moeda = moeda;
    }
}
