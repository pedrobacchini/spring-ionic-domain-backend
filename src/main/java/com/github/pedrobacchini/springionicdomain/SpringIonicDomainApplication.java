package com.github.pedrobacchini.springionicdomain;

import com.github.pedrobacchini.springionicdomain.domain.*;
import com.github.pedrobacchini.springionicdomain.enums.EstadoPagamento;
import com.github.pedrobacchini.springionicdomain.enums.TipoCliente;
import com.github.pedrobacchini.springionicdomain.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@SpringBootApplication
public class SpringIonicDomainApplication implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public SpringIonicDomainApplication(CategoriaRepository categoriaRepository,
                                        ProdutoRepository produtoRepository,
                                        EstadoRepository estadoRepository,
                                        CidadeRepository cidadeRepository,
                                        EnderecoRepository enderecoRepository,
                                        ClienteRepository clienteRepository,
                                        PedidoRepository pedidoRepository,
                                        PagamentoRepository pagamentoRepository,
                                        ItemPedidoRepository itemPedidoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.estadoRepository = estadoRepository;
        this.cidadeRepository = cidadeRepository;
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIonicDomainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Categoria categoria1 = new Categoria(null, "Informática");
        Categoria categoria2 = new Categoria(null, "Escrítorio");
        Categoria categoria3 = new Categoria(null, "Cama, mesa e banho");
        Categoria categoria4 = new Categoria(null, "Eletrônicos");
        Categoria categoria5 = new Categoria(null, "Jardinagem");
        Categoria categoria6 = new Categoria(null, "Decoração");
        Categoria categoria7 = new Categoria(null, "Perfumaria");

        Produto produto1 = new Produto(null, "Computador", 2000D);
        Produto produto2 = new Produto(null, "Impressora", 800D);
        Produto produto3 = new Produto(null, "Mouse", 80D);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().add(produto2);

        produto1.getCategorias().add(categoria1);
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
        produto3.getCategorias().add(categoria1);

        categoriaRepository.saveAll(Arrays.asList(categoria1,
                categoria2, categoria3, categoria4,
                categoria5, categoria6, categoria7));

        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));

        Estado estado1 = new Estado(null, "Minas Gerais");
        Estado estado2 = new Estado(null, "São Paulo");

        Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
        Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
        Cidade cidade3 = new Cidade(null, "Campinas", estado2);

        estado1.getCidades().add(cidade1);
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente(null, "maria", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
        cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
        Endereco endereco1 = new Endereco(null, "Rua Flores",
                "300", "Apto 203",
                "Jardim", "38220834", cliente1, cidade1);
        Endereco endereco2 = new Endereco(null, "Avenida Matos",
                "105","Sala 800",
                "Centro", "38777012", cliente1, cidade2);
        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

        clienteRepository.save(cliente1);
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

        SimpleDateFormat formatDiaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat formatDia = new SimpleDateFormat("dd-MM-yyyy");
        Date instante1 = new Date(), instante2 = new Date(), dataVencimento = new Date();
        try {
            instante1 = formatDiaHora.parse("30-09-0217 10:32");
            instante2 = formatDiaHora.parse("10-10-2017 19:35");
            dataVencimento = formatDia.parse("20-10-2017");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Pedido pedido1 = new Pedido(instante1, endereco1, cliente1);
        Pagamento pagamentoComCartao = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamentoComCartao);

        Pedido pedido2 = new Pedido(instante2, endereco2, cliente1);
        Pagamento pagamentoComBoleto = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, dataVencimento, null);
        pedido2.setPagamento(pagamentoComBoleto);

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamentoComBoleto, pagamentoComCartao));

//        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
//        clienteRepository.save(cliente1);

        ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0D, 1, 2000D);
        ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0D, 2, 80D);
        pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));

        ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100D, 1, 800D);
        pedido2.getItens().add(itemPedido3);

        produto1.getItens().add(itemPedido1);
        produto2.getItens().add(itemPedido3);
        produto3.getItens().add(itemPedido2);

        itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }
}
