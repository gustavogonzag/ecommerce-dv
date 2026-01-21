package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.ItemPedidoRequestDTO;
import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.dto.response.PedidoResponseDTO;
import com.gustavo.ecommerce.entity.*;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import com.gustavo.ecommerce.repository.*;
import com.gustavo.ecommerce.service.PedidoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gustavo.ecommerce.repository.BairroRepository;
import com.gustavo.ecommerce.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final BairroRepository bairroRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoServiceImpl(BairroRepository bairroRepository,
                             PedidoRepository pedidoRepository) {
        this.bairroRepository = bairroRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {

        var bairro = bairroRepository.findById(dto.getBairroId())
                .orElseThrow(() -> new RuntimeException("Bairro não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setNomeCliente(dto.getNomeCliente());
        pedido.setTelefone(dto.getTelefone());
        pedido.setEndereco(dto.getEndereco());
        pedido.setBairro(bairro);
        pedido.setStatus(StatusPedido.ACEITO);
        pedido.setDataHora(LocalDateTime.now());



        return null;
    }
}

