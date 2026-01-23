package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.ItemPedidoRequestDTO;
import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.entity.Bairro;
import com.gustavo.ecommerce.entity.ItemPedido;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.Produto;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import com.gustavo.ecommerce.repository.BairroRepository;
import com.gustavo.ecommerce.repository.PedidoRepository;
import com.gustavo.ecommerce.repository.ProdutoRepository;
import com.gustavo.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Pedido criarPedido(PedidoRequestDTO dto) {

        Pedido pedido = new Pedido();

        // 🔹 Dados do cliente
        pedido.setNomeCliente(dto.getNomeCliente());
        pedido.setTelefone(dto.getTelefone());
        pedido.setEndereco(dto.getEndereco());

        // 🔹 Bairro
        Bairro bairro = bairroRepository.findById(dto.getBairroId())
                .orElseThrow(() -> new RuntimeException("Bairro não encontrado"));

        pedido.setBairro(bairro);
        pedido.setTaxaEntrega(bairro.getTaxaEntrega());

        // 🔹 Status inicial
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setFormaPagamento(dto.getFormaPagamento());

        BigDecimal subtotalPedido = BigDecimal.ZERO;
        List<ItemPedido> itens = new ArrayList<>();

        // 🔹 Itens do pedido
        for (ItemPedidoRequestDTO itemDTO : dto.getItens()) {

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());

            BigDecimal subtotalItem = produto.getPreco()
                    .multiply(BigDecimal.valueOf(itemDTO.getQuantidade()));

            item.setSubtotal(subtotalItem);

            subtotalPedido = subtotalPedido.add(subtotalItem);
            itens.add(item);
        }

        pedido.setItens(itens);
        pedido.setSubtotal(subtotalPedido);
        pedido.setTotal(subtotalPedido.add(bairro.getTaxaEntrega()));

        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido atualizarStatusPedido(Integer id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // 🔥 aqui você altera SOMENTE o status
        pedido.setStatus(novoStatus);

        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map( pedido -> {
                    Pedido res = new Pedido();
                    res.setId(pedido.getId());
                    res.setNomeCliente(pedido.getNomeCliente());
                    res.setTelefone(pedido.getTelefone());
                    res.setEndereco(pedido.getEndereco());
                    res.setBairro(pedido.getBairro());
                    res.setStatus(pedido.getStatus());
                    res.setDataHora(pedido.getDataHora());
                    res.setSubtotal(pedido.getSubtotal());
                    res.setTotal(pedido.getTotal());
                    res.setItens(pedido.getItens());
                    return res;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findByStatusOrderByDataCriacaoDesc(StatusPedido status) {
        return pedidoRepository.findByStatusOrderByDataCriacaoDesc(status);
    }

    @Override
    public Pedido buscarPedidoPorId(Integer id) {

        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

}
