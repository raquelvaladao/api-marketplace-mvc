package com.api.estudo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 20)
    private String nome;

    @Email
    @NotNull
    private String email;

    @JsonIgnore
    @NotNull
    private String senha;

    @ManyToOne
    @NotNull
    private Perfil perfil;

    @Column(name = "quantidade_amigos")
    private Integer quantidadeAmigos;

    @OneToMany(mappedBy = "usuario",cascade = {PERSIST, REMOVE, REFRESH})
    //@JoinColumn(name = "usuario_id")
    private List<Carteira> carteira = new ArrayList<>();

    @OneToMany(mappedBy = "usuario",cascade = {PERSIST, REMOVE})
    private List<Produto> produto = new ArrayList<>();

    @NotNull
    @PositiveOrZero(message = "Não é aceito números negativos")
    @Digits(integer = 19,fraction = 2)
    private BigDecimal saldo;

    public Usuario(Long id, String nome, String email, String senha, Perfil perfil, List<Carteira> carteira) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.quantidadeAmigos = 0;
        this.carteira = carteira;
        this.produto = new ArrayList<>();
        this.saldo = BigDecimal.ZERO;
    }

    public Usuario(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(perfil);
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
