package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.ItemPedidoRequestDTO;
import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.dto.response.PageResponseDTO;
import com.gustavo.ecommerce.dto.response.PedidoResponseDTO;
import com.gustavo.ecommerce.entity.Bairro;
import com.gustavo.ecommerce.entity.ItemPedido;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.Produto;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import com.gustavo.ecommerce.exception.ResourceNotFoundException;
import com.gustavo.ecommerce.mapper.PedidoMapper;
import com.gustavo.ecommerce.repository.BairroRepository;
import com.gustavo.ecommerce.repository.PedidoRepository;
import com.gustavo.ecommerce.repository.ProdutoRepository;
import com.gustavo.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.gustavo.ecommerce.repository.specification.PedidoSpecification.*;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Override
    @Transactional
    public Pedido criarPedido(PedidoRequestDTO dto) {

        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter ao menos um item");
        }

        Pedido pedido = new Pedido();

        pedido.setNomeCliente(dto.getNomeCliente());
        pedido.setTelefone(dto.getTelefone());
        pedido.setEndereco(dto.getEndereco());

        Bairro bairro = bairroRepository.findById(dto.getBairroId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bairro não encontrado")
                );

        pedido.setBairro(bairro);
        pedido.setTaxaEntrega(bairro.getTaxaEntrega());

        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setFormaPagamento(dto.getFormaPagamento());

        BigDecimal subtotalPedido = BigDecimal.ZERO;
        List<ItemPedido> itens = new ArrayList<>();

        for (ItemPedidoRequestDTO itemDTO : dto.getItens()) {

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Produto não encontrado (id=" + itemDTO.getProdutoId() + ")"
                            )
                    );

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
    @Transactional
    public Pedido atualizarStatusPedido(Integer id, StatusPedido novoStatus) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado")
                );

        pedido.setStatus(novoStatus);

        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public Pedido buscarPedidoPorId(Integer id) {

        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado")
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pedido> listarComFiltros(
            StatusPedido status,
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable
    ) {

        return pedidoRepository.findAll(
                Specification
                        .where(comStatus(status))
                        .and(dataInicio(dataInicio))
                        .and(dataFim(dataFim)),
                pageable
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pedido> listarUltimosPedidos(Pageable pageable) {
        return pedidoRepository.findAllByOrderByDataCriacaoDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> listarPedidosAtrasados(int minutos) {

        LocalDateTime limite = LocalDateTime.now().minusMinutes(minutos);
        return pedidoRepository.findPedidosAtrasados(limite);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<PedidoResponseDTO> listarPedidosPaginados(
            StatusPedido status,
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable
    ) {

        Page<Pedido> page = pedidoRepository.findAll(
                Specification
                        .where(comStatus(status))
                        .and(dataInicio(dataInicio))
                        .and(dataFim(dataFim)),
                pageable
        );

        return pedidoMapper.toPageResponse(page);
    }
}
