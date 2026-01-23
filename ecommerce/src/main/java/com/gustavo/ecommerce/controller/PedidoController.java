package com.gustavo.ecommerce.controller;

import com.gustavo.ecommerce.dto.request.AtualizarStatusPedidoDTO;
import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import com.gustavo.ecommerce.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody PedidoRequestDTO dto) {
        Pedido pedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(
            @PathVariable Integer id,
            @RequestBody @Valid AtualizarStatusPedidoDTO dto) {

        System.out.println("Statys recebido: " + dto);
        Pedido pedidoAtualizado = pedidoService.atualizarStatusPedido(id, dto.getStatusPedido());

        return ResponseEntity.ok(pedidoAtualizado);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Pedido>> buscarPorStatus(
            @RequestParam StatusPedido status
    ) {
        return ResponseEntity.ok(pedidoService.findByStatusOrderByDataCriacaoDesc(status));
    }

    @GetMapping("/data")
    public ResponseEntity<List<Pedido>> buscarPorData(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate data
    ) {
        return ResponseEntity.ok(pedidoService.buscarPedidosPorData(data));
    }

}

