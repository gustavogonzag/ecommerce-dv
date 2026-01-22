package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;

import java.util.List;

public interface ProdutoService {

    ProdutoRequestDTO criar(ProdutoRequestDTO dto);
    ProdutoRequestDTO atualizarProduto(ProdutoRequestDTO dto);
    List<ProdutoRequestDTO> listar();
    void apagarProduto(Integer id);
}
