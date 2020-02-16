package com.github.pedrobacchini.springionicdomain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pedrobacchini.springionicdomain.enums.Role;
import com.github.pedrobacchini.springionicdomain.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Client implements Serializable {

    private static final long serialVersionUID = 8699782015209935297L;

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Column(unique = true)
    private String email;

    @Getter
    private String cpfOrCnpj;

    @Getter
    private Integer type;

    @Getter
    @Setter
    @JsonIgnore
    private String password;

    @Getter
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @Getter
    @ElementCollection
    @CollectionTable(name = "PHONES")
    private Set<String> phones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES")
    private Set<Integer> roles = new HashSet<>();

    @Getter
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Pedido> pedidos = new ArrayList<>();

    public Client() { addRole(Role.CLIENT); }

    public Client(Integer id, String name, String email, String cpfOrCnpj, ClientType type, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpfOrCnpj = cpfOrCnpj;
        this.type = (type == null) ? null : type.getCod();
        this.password = password;
        addRole(Role.CLIENT);
    }

    public Set<Role> getRoles() { return roles.stream().map(Role::toEnum).collect(Collectors.toSet()); }

    public void addRole(Role role) { roles.add(role.getCod()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
