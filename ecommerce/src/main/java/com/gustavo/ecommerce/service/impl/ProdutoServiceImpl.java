package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;
import com.gustavo.ecommerce.dto.response.ProdutoResponseDTO;
import com.gustavo.ecommerce.entity.Categoria;
import com.gustavo.ecommerce.entity.Produto;
import com.gustavo.ecommerce.repository.CategoriaRepository;
import com.gustavo.ecommerce.repository.ProdutoRepository;
import com.gustavo.ecommerce.service.ProdutoService;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository,
                              CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Produto produto = new Produto(
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                categoria
        );

        produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                categoria.getNome()
        );
    }
}
