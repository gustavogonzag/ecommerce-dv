package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.CategoriaRequestDTO;
import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;
import com.gustavo.ecommerce.dto.response.ProdutoResponseDTO;
import com.gustavo.ecommerce.entity.Categoria;
import com.gustavo.ecommerce.entity.Produto;
import com.gustavo.ecommerce.repository.CategoriaRepository;
import com.gustavo.ecommerce.repository.ProdutoRepository;
import com.gustavo.ecommerce.service.ProdutoService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ProdutoRequestDTO criar(ProdutoRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setCategoria(categoria);
        produto.setPreco(dto.getPreco());
        produto.setAtivo(dto.isAtivo());

        Produto salvo = produtoRepository.save(produto);

        ProdutoRequestDTO response = new ProdutoRequestDTO();
        response.setId(salvo.getId());
        response.setNome(salvo.getNome());
        response.setDescricao(salvo.getDescricao());
        response.setPreco(salvo.getPreco());
        response.setAtivo(salvo.getAtivo());
        response.setCategoriaId(salvo.getCategoria().getId());

        return response;
    }

    @Override
    public ProdutoRequestDTO atualizarProduto(ProdutoRequestDTO dto) {

        Produto produto = produtoRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setAtivo(dto.isAtivo());

        Produto atualizado = produtoRepository.save(produto);

        ProdutoRequestDTO response = new ProdutoRequestDTO();
        response.setId(atualizado.getId());
        response.setNome(atualizado.getNome());
        response.setDescricao(atualizado.getDescricao());
        response.setPreco(atualizado.getPreco());
        response.setAtivo(dto.isAtivo());

        return response;
    }

    @Override
    public List<ProdutoResponseDTO> listar() {
        return List.of();
    }
}
