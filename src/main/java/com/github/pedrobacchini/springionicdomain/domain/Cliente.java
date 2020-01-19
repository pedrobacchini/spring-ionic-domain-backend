package com.github.pedrobacchini.springionicdomain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pedrobacchini.springionicdomain.enums.Perfil;
import com.github.pedrobacchini.springionicdomain.enums.TipoCliente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 8699782015209935297L;

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    @Column(unique = true)
    private String email;

    @Getter
    private String cpfOuCnpj;

    @Getter
    private Integer tipo;

    @Getter
    @Setter
    @JsonIgnore
    private String senha;

    @Getter
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @Getter
    @ElementCollection
    @CollectionTable(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    @Getter
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() { addPerfil(Perfil.CLIENT); }

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = (tipo == null) ? null : tipo.getCod();
        this.senha = senha;
        addPerfil(Perfil.CLIENT);
    }

    public Set<Perfil> getPerfis() { return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet()); }

    public void addPerfil(Perfil perfil) { perfis.add(perfil.getCod()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
