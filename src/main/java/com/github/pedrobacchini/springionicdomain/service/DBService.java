package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.*;
import com.github.pedrobacchini.springionicdomain.enums.EstadoPagamento;
import com.github.pedrobacchini.springionicdomain.enums.Role;
import com.github.pedrobacchini.springionicdomain.enums.ClientType;
import com.github.pedrobacchini.springionicdomain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class DBService {

    private final CategoryRepository categoryRepository;
    private final ProdutoRepository produtoRepository;
    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;
    private final ClientRepository clientRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void instantiateTestDatabase() throws ParseException {

        Category category1 = new Category(null, "Informática");
        Category category2 = new Category(null, "Escrítorio");
        Category category3 = new Category(null, "Cama, mesa e banho");
        Category category4 = new Category(null, "Eletrônicos");
        Category category5 = new Category(null, "Jardinagem");
        Category category6 = new Category(null, "Decoração");
        Category category7 = new Category(null, "Perfumaria");

        Produto produto1 = new Produto(null, "Computador", 2000D);
        Produto produto2 = new Produto(null, "Impressora", 800D);
        Produto produto3 = new Produto(null, "Mouse", 80D);
        Produto produto4 = new Produto(null, "Mesa de escritorio", 300D);
        Produto produto5 = new Produto(null, "Toalha", 50D);
        Produto produto6 = new Produto(null, "Colcha", 200D);
        Produto produto7 = new Produto(null, "TV true color", 1200D);
        Produto produto8 = new Produto(null, "Roçadeira", 800D);
        Produto produto9 = new Produto(null, "Abajour", 100D);
        Produto produto10 = new Produto(null, "Pendente", 180D);
        Produto produto11 = new Produto(null, "Shampoo", 90D);
        Produto produto12 = new Produto(null, "Produto 12", 10.00);
        Produto produto13 = new Produto(null, "Produto 13", 10.00);
        Produto produto14 = new Produto(null, "Produto 14", 10.00);
        Produto produto15 = new Produto(null, "Produto 15", 10.00);
        Produto produto16 = new Produto(null, "Produto 16", 10.00);
        Produto produto17 = new Produto(null, "Produto 17", 10.00);
        Produto produto18 = new Produto(null, "Produto 18", 10.00);
        Produto produto19 = new Produto(null, "Produto 19", 10.00);
        Produto produto20 = new Produto(null, "Produto 20", 10.00);
        Produto produto21 = new Produto(null, "Produto 21", 10.00);
        Produto produto22 = new Produto(null, "Produto 22", 10.00);
        Produto produto23 = new Produto(null, "Produto 23", 10.00);
        Produto produto24 = new Produto(null, "Produto 24", 10.00);
        Produto produto25 = new Produto(null, "Produto 25", 10.00);
        Produto produto26 = new Produto(null, "Produto 26", 10.00);
        Produto produto27 = new Produto(null, "Produto 27", 10.00);
        Produto produto28 = new Produto(null, "Produto 28", 10.00);
        Produto produto29 = new Produto(null, "Produto 29", 10.00);
        Produto produto30 = new Produto(null, "Produto 30", 10.00);
        Produto produto31 = new Produto(null, "Produto 31", 10.00);
        Produto produto32 = new Produto(null, "Produto 32", 10.00);
        Produto produto33 = new Produto(null, "Produto 33", 10.00);
        Produto produto34 = new Produto(null, "Produto 34", 10.00);
        Produto produto35 = new Produto(null, "Produto 35", 10.00);
        Produto produto36 = new Produto(null, "Produto 36", 10.00);
        Produto produto37 = new Produto(null, "Produto 37", 10.00);
        Produto produto38 = new Produto(null, "Produto 38", 10.00);
        Produto produto39 = new Produto(null, "Produto 39", 10.00);
        Produto produto40 = new Produto(null, "Produto 40", 10.00);
        Produto produto41 = new Produto(null, "Produto 41", 10.00);
        Produto produto42 = new Produto(null, "Produto 42", 10.00);
        Produto produto43 = new Produto(null, "Produto 43", 10.00);
        Produto produto44 = new Produto(null, "Produto 44", 10.00);
        Produto produto45 = new Produto(null, "Produto 45", 10.00);
        Produto produto46 = new Produto(null, "Produto 46", 10.00);
        Produto produto47 = new Produto(null, "Produto 47", 10.00);
        Produto produto48 = new Produto(null, "Produto 48", 10.00);
        Produto produto49 = new Produto(null, "Produto 49", 10.00);
        Produto produto50 = new Produto(null, "Produto 50", 10.00);

        category1.addAllProduto(Arrays.asList(produto1, produto2, produto3));
        category2.addAllProduto(Arrays.asList(produto2, produto4));
        category3.addAllProduto(Arrays.asList(produto5, produto6));
        category4.addAllProduto(Arrays.asList(produto1, produto2, produto3, produto7));
        category5.addProduto(produto8);
        category6.addAllProduto(Arrays.asList(produto9, produto10));
        category7.addProduto(produto11);

        category1.addAllProduto(Arrays.asList(produto12, produto13, produto14, produto15, produto16, produto17,
                produto18, produto19, produto20, produto21, produto22, produto23, produto24, produto25,
                produto26, produto27, produto28, produto29, produto30, produto31, produto32, produto33,
                produto34, produto35, produto36, produto37, produto38, produto39, produto40, produto41,
                produto42, produto43, produto44, produto45, produto46, produto47, produto48, produto49,
                produto50));

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3, category4,
                category5, category6, category7));

        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5,
                produto6, produto7, produto8, produto9, produto10, produto11, produto12, produto13,
                produto14, produto15, produto16, produto17, produto18, produto19, produto20, produto21,
                produto22, produto23, produto24, produto25, produto26, produto27, produto28, produto29,
                produto30, produto31, produto32, produto33, produto34, produto35, produto36, produto37,
                produto38, produto39, produto40, produto41, produto42, produto43, produto44, produto45,
                produto46, produto47, produto48, produto49, produto50));

        Estado estado1 = new Estado(null, "Minas Gerais");
        Estado estado2 = new Estado(null, "São Paulo");

        Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
        Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
        Cidade cidade3 = new Cidade(null, "Campinas", estado2);

        estado1.getCidades().add(cidade1);
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Client mariaSilva = new Client(null,
                "Maria Silva",
                "pedroheinrique@gmail.com",
                "36378912377",
                ClientType.INDIVIDUAL,
                bCryptPasswordEncoder.encode("123"));
        mariaSilva.getPhones().addAll(Arrays.asList("27363323", "93838393"));

        Client anaCosta = new Client(null,
                "Ana Costa",
                "pedrobacchini@outlook.com",
                "68813512244",
                ClientType.INDIVIDUAL,
                bCryptPasswordEncoder.encode("123"));
        anaCosta.getPhones().add("66996193389");
        anaCosta.addRole(Role.ADMIN);

        Endereco endereco1 = new Endereco(null, "Rua Flores",
                "300", "Apto 203",
                "Jardim", "38220834", mariaSilva, cidade1);
        Endereco endereco2 = new Endereco(null, "Avenida Matos",
                "105", "Sala 800",
                "Centro", "38777012", mariaSilva, cidade2);
        Endereco endereco3 = new Endereco(null, "Avenida Floriano",
                "2106", null,
                "Centro", "5646546", anaCosta, cidade2);

        mariaSilva.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
        anaCosta.getEnderecos().add(endereco3);

        clientRepository.saveAll(Arrays.asList(mariaSilva, anaCosta));
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));

        SimpleDateFormat formatDiaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat formatDia = new SimpleDateFormat("dd-MM-yyyy");
        Date instante1;
        Date instante2;
        Date dataVencimento;

        instante1 = formatDiaHora.parse("30-09-0217 10:32");
        instante2 = formatDiaHora.parse("10-10-2017 19:35");
        dataVencimento = formatDia.parse("20-10-2017");

        Pedido pedido1 = new Pedido(instante1, endereco1, mariaSilva);
        Pagamento pagamentoComCartao = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamentoComCartao);

        Pedido pedido2 = new Pedido(instante2, endereco2, mariaSilva);
        Pagamento pagamentoComBoleto = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, dataVencimento, null);
        pedido2.setPagamento(pagamentoComBoleto);

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamentoComBoleto, pagamentoComCartao));

//        client1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
//        clientRepository.save(client1);

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
