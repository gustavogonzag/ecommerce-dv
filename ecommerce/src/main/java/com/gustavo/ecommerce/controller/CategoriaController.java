package com.gustavo.ecommerce.controller;

import com.gustavo.ecommerce.dto.request.CategoriaRequestDTO;
import com.gustavo.ecommerce.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaRequestDTO>> findAll() {
        return ResponseEntity.ok(service.listarCategorias());
    }

    @PostMapping
    public ResponseEntity<CategoriaRequestDTO> cadastrarCategoria(
            @Valid @RequestBody CategoriaRequestDTO dto) {

        CategoriaRequestDTO res = service.criarCategoria(dto);
        return ResponseEntity.status(201).body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRequestDTO> atualizarCategoria(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDTO dto) {

        dto.setId(id);
        return ResponseEntity.ok(service.atualizarCategoria(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Integer id) {
        service.apagarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRequestDTO> buscarCategoriaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarCategoriaPorId(id));
    }
}

