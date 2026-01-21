package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;
import com.gustavo.ecommerce.dto.response.ProdutoResponseDTO;

public interface ProdutoService {

    ProdutoResponseDTO criar(ProdutoRequestDTO dto);
}
