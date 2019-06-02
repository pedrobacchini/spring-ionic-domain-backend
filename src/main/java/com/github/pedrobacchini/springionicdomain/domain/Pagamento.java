package com.github.pedrobacchini.springionicdomain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pedrobacchini.springionicdomain.enums.EstadoPagamento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
public abstract class Pagamento implements Serializable {

    private static final long serialVersionUID = -1561739326966717779L;

    @Id
    private Integer id;

    private Integer estado;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

    public Pagamento() { }

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        this.id = id;
        this.estado = (estado==null) ? null : estado.getCod();
        this.pedido = pedido;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public EstadoPagamento getEstado() { return EstadoPagamento.toEnum(estado); }

    public void setEstado(EstadoPagamento estado) { this.estado = estado.getCod(); }

    public Pedido getPedido() { return pedido; }

    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
