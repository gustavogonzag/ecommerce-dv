package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.enums.StatusPedido;

import java.util.List;

public interface PedidoService {

    Pedido criarPedido(PedidoRequestDTO dto);
    Pedido atualizarStatusPedido(Integer id, StatusPedido novoStatus);
    List<Pedido> listarPedidos();
    List<Pedido> findByStatusOrderByDataCriacaoDesc(StatusPedido status);
    Pedido buscarPedidoPorId(Integer id);
}
