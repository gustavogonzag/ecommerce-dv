package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.CategoriaRequestDTO;
import com.gustavo.ecommerce.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    CategoriaRequestDTO criarCategoria(CategoriaRequestDTO categoriaNova);
    CategoriaRequestDTO atualizarCategoria(CategoriaRequestDTO categoria);
    List<CategoriaRequestDTO> listarCategorias();
    void apagarCategoria(Integer id);
    CategoriaRequestDTO buscarCategoriaPorId(Integer id);
}
