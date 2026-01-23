package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.entity.Pedido;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PedidoService {

    Pedido criarPedido(PedidoRequestDTO dto);
    Pedido atualizarPedido(PedidoRequestDTO dto);
    List<Pedido> listarPedidos();
    List<Pedido> listarPedidosByStatus(String status);
    Pedido buscarPedidoPorId(Integer id);
}
