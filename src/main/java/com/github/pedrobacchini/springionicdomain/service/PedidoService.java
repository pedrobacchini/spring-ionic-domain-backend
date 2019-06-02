package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.PagamentoComBoleto;
import com.github.pedrobacchini.springionicdomain.domain.Pedido;
import com.github.pedrobacchini.springionicdomain.enums.EstadoPagamento;
import com.github.pedrobacchini.springionicdomain.repository.ItemPedidoRepository;
import com.github.pedrobacchini.springionicdomain.repository.PagamentoRepository;
import com.github.pedrobacchini.springionicdomain.repository.PedidoRepository;
import com.github.pedrobacchini.springionicdomain.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PedidoService {

    private final BoletoService boletoService;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;

    public PedidoService(PedidoRepository pedidoRepository,
                         BoletoService boletoService,
                         PagamentoRepository pagamentoRepository,
                         ItemPedidoRepository itemPedidoRepository,
                         ProdutoService produtoService, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.boletoService = boletoService;
        this.pagamentoRepository = pagamentoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    public Pedido find(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                                + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if(pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        final Pedido finalPedido = pedido;
        pedido.getItens().forEach(itemPedido -> {
            itemPedido.setDesconto(0.0);
            itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            itemPedido.setPedido(finalPedido);
        });
        itemPedidoRepository.saveAll(pedido.getItens());
        System.out.println(pedido.toString());
        return pedido;
    }
}
