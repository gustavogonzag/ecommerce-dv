package com.gustavo.ecommerce.controller;

import com.gustavo.ecommerce.dto.request.AtualizarStatusPedidoDTO;
import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.dto.response.PageResponseDTO;
import com.gustavo.ecommerce.dto.response.PedidoResponseDTO;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import com.gustavo.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // =========================
    // CRIAR PEDIDO
    // =========================
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoRequestDTO dto) {
        Pedido pedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    // =========================
    // ATUALIZAR STATUS
    // =========================
    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(
            @PathVariable Integer id,
            @RequestBody AtualizarStatusPedidoDTO dto
            ) {
        Pedido pedido = pedidoService.atualizarStatusPedido(id, dto.getStatusPedido());
        return ResponseEntity.ok(pedido);
    }

    // =========================
    // LISTAR PAGINADO + FILTROS
    // =========================
    @GetMapping
    public ResponseEntity<PageResponseDTO<PedidoResponseDTO>> listarPedidos(
            @RequestParam(required = false) StatusPedido status,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataInicio,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataFim,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "dataCriacao")
        );

        PageResponseDTO<PedidoResponseDTO> response =
                pedidoService.listarPedidosPaginados(
                        status,
                        dataInicio,
                        dataFim,
                        pageable
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hoje")
    public ResponseEntity<Page<Pedido>> pedidosDeHoje(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LocalDate hoje = LocalDate.now();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("dataCriacao").descending()
        );

        return ResponseEntity.ok(
                pedidoService.listarComFiltros(
                        null,
                        hoje,
                        hoje,
                        pageable
                )
        );
    }

    @GetMapping("/ultimos")
    public ResponseEntity<Page<Pedido>> ultimosPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(pedidoService.listarUltimosPedidos(pageable));
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<Pedido>> pedidosAtrasados(
            @RequestParam(defaultValue = "50") int minutos
    ) {
        return ResponseEntity.ok(
                pedidoService.listarPedidosAtrasados(minutos)
        );
    }


}
