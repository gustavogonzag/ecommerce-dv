package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.ItemPedidoRequestDTO;
import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.dto.response.PedidoResponseDTO;
import com.gustavo.ecommerce.entity.ItemPedido;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.Produto;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import com.gustavo.ecommerce.repository.BairroRepository;
import com.gustavo.ecommerce.repository.PedidoRepository;
import com.gustavo.ecommerce.repository.ProdutoRepository;
import com.gustavo.ecommerce.service.PedidoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final BairroRepository bairroRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoServiceImpl(BairroRepository bairroRepository,
                             PedidoRepository pedidoRepository,
                             ProdutoRepository produtoRepository) {
        this.bairroRepository = bairroRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.ACEITO);
        pedido.setDataHora(LocalDateTime.now());

        BigDecimal subtotal = BigDecimal.ZERO;
        List<ItemPedido> itens = new ArrayList<>();

        for (ItemPedidoRequestDTO itemDto : dto.getItens()) {

            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            BigDecimal precoUnitario = produto.getPreco();
            int quantidade = itemDto.getQuantidade();

            BigDecimal subtotalItem = precoUnitario
                    .multiply(BigDecimal.valueOf(quantidade))
                    .setScale(2, RoundingMode.HALF_UP);

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(precoUnitario);
            item.setSubtotal(subtotalItem);
            item.setPedido(pedido);

            itens.add(item);
            subtotal = subtotal.add(subtotalItem);
        }

        pedido.setSubtotal(subtotal);
        pedido.setItens(itens);

        return null;
    }
}
