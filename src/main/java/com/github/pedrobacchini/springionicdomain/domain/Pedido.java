package com.github.pedrobacchini.springionicdomain.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = -2014723447993124458L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instante;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address addressDeEntrega;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido() { }

    public Pedido(Date instante, Address addressDeEntrega, Client client) {
        this.instante = instante;
        this.addressDeEntrega = addressDeEntrega;
        this.client = client;
    }

    public double getValorTotal() { return itens.stream().mapToDouble(ItemPedido::getSubTotal).sum(); }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Date getInstante() { return instante; }

    public void setInstante(Date instante) { this.instante = instante; }

    public Address getAddressDeEntrega() { return addressDeEntrega; }

    public void setAddressDeEntrega(Address addressDeEntrega) { this.addressDeEntrega = addressDeEntrega; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public Pagamento getPagamento() { return pagamento; }

    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }

    public Set<ItemPedido> getItens() { return itens; }

    public void setItens(Set<ItemPedido> itens) { this.itens = itens; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd//MM/yyy hh:mm:ss");
        final StringBuilder sb = new StringBuilder();
        sb.append("Pedido número: ");
        sb.append(getId());
        sb.append(", Instante: ");
        sb.append(sdf.format(getInstante()));
        sb.append(", Client: ");
        sb.append(getClient().getName());
        sb.append(", Situação do pagamento: ");
        sb.append(getPagamento().getEstado().getDescricao());
        sb.append("\nDetalhes:\n");
        for (ItemPedido ip : getItens())
            sb.append(ip.toString());
        sb.append("Valor total: ");
        sb.append(nf.format(getValorTotal()));
        return sb.toString();
    }
}
