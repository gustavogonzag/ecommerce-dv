package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.CategoriaRequestDTO;
import com.gustavo.ecommerce.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    public CategoriaRequestDTO criarCategoria(CategoriaRequestDTO categoriaNova);
    public CategoriaRequestDTO atualizarCategoria(CategoriaRequestDTO categoria);
    public List<CategoriaRequestDTO> listarCategorias();
    public void apagarCategoria(Integer id);
}
