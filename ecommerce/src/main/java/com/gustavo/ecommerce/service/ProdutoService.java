package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;
import com.gustavo.ecommerce.entity.Produto;

import java.util.List;

public interface ProdutoService {

    ProdutoRequestDTO criar(ProdutoRequestDTO dto);
    ProdutoRequestDTO atualizarProduto(ProdutoRequestDTO dto);
    List<ProdutoRequestDTO> listar();
    ProdutoRequestDTO buscarProdutoPorId(Integer id);
    void apagarProduto(Integer id);
}
