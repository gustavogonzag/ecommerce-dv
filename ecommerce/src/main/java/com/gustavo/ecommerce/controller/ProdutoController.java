package com.gustavo.ecommerce.controller;

import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;
import com.gustavo.ecommerce.dto.response.ProdutoResponseDTO;
import com.gustavo.ecommerce.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ProdutoResponseDTO criar(@RequestBody ProdutoRequestDTO dto) {
        return produtoService.criar(dto);
    }

    @GetMapping
    public List<ProdutoResponseDTO> listar() {
        return produtoService.listar();
    }
}

