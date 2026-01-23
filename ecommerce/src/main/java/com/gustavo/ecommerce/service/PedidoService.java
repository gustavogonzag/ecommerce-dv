package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;

public interface PedidoService {

    PedidoRequestDTO criarPedido(PedidoRequestDTO dto);
    PedidoRequestDTO atualizarPedido(PedidoRequestDTO dto);
}
