package com.api.estudo.entities;

import com.api.estudo.enums.StatusAmizade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_amizade")
public class Amizade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Usuario remetente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Usuario destinatario;

    @NotNull(message = "Data inválida")
    @Column(name = "data_envio")
    private Date dataEnvio;

    @NotNull(message = "Status de pedido inválido")
    @Enumerated(EnumType.STRING)
    private StatusAmizade status;

}
