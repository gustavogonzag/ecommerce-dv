package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.enums.StatusPedido;

import java.time.LocalDate;
import java.util.List;

public interface PedidoService {

    Pedido criarPedido(PedidoRequestDTO dto);
    Pedido atualizarStatusPedido(Integer id, StatusPedido novoStatus);
    List<Pedido> listarPedidos();
    List<Pedido> findByStatusOrderByDataCriacaoDesc(StatusPedido status);
    List<Pedido> buscarPedidosPorData(LocalDate data);
    Pedido buscarPedidoPorId(Integer id);
}
