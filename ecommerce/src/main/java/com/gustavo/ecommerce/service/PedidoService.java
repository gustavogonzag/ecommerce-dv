package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.dto.response.PedidoResponseDTO;

public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoRequestDTO dto);
    PedidoResponseDTO atualizarPedido(PedidoRequestDTO dto);
}
