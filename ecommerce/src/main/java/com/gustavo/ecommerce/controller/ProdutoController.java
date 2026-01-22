package com.gustavo.ecommerce.controller;

import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;
import com.gustavo.ecommerce.dto.response.ProdutoResponseDTO;
import com.gustavo.ecommerce.service.ProdutoService;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoRequestDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {

        ProdutoRequestDTO res = produtoService.criar(dto);

        if (res != null) {
            return ResponseEntity.status(201).body(res);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRequestDTO> atualizar(@PathVariable int id, @RequestBody ProdutoRequestDTO dto) {
        dto.setId(id);
        ProdutoRequestDTO res = produtoService.atualizarProduto(dto);
        if (res != null) {
            return ResponseEntity.status(200).body(res);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> findAll(){
        return ResponseEntity.ok(produtoService.listar());
    }
}

