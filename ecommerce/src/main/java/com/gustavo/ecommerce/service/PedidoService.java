package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.dto.response.PageResponseDTO;
import com.gustavo.ecommerce.dto.response.PedidoResponseDTO;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface PedidoService {

    Pedido criarPedido(PedidoRequestDTO dto);
    Pedido atualizarStatusPedido(Integer id, StatusPedido novoStatus);
    Pedido buscarPedidoPorId(Integer id);

    Page<Pedido> listarComFiltros(
            StatusPedido status,
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable
    );

    Page<Pedido> listarUltimosPedidos(Pageable pageable);
    List<Pedido> listarPedidosAtrasados(int minutos);

    PageResponseDTO<PedidoResponseDTO> listarPedidosPaginados(
            StatusPedido status,
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable
    );
}
