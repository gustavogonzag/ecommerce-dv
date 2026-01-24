package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;
import com.gustavo.ecommerce.entity.Categoria;
import com.gustavo.ecommerce.entity.Produto;
import com.gustavo.ecommerce.exception.ResourceNotFoundException;
import com.gustavo.ecommerce.repository.CategoriaRepository;
import com.gustavo.ecommerce.repository.ProdutoRepository;
import com.gustavo.ecommerce.service.ProdutoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoServiceImpl(
            ProdutoRepository produtoRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ProdutoRequestDTO criar(ProdutoRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Categoria não encontrada")
                );

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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado")
                );

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
        response.setAtivo(atualizado.getAtivo());
        response.setCategoriaId(atualizado.getCategoria().getId());

        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProdutoRequestDTO> listar() {

        return produtoRepository.findAll()
                .stream()
                .map(produto -> {
                    ProdutoRequestDTO res = new ProdutoRequestDTO();
                    res.setId(produto.getId());
                    res.setNome(produto.getNome());
                    res.setDescricao(produto.getDescricao());
                    res.setPreco(produto.getPreco());
                    res.setAtivo(produto.getAtivo());
                    res.setCategoriaId(produto.getCategoria().getId());
                    return res;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ProdutoRequestDTO buscarProdutoPorId(Integer id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado")
                );

        ProdutoRequestDTO dto = new ProdutoRequestDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setAtivo(produto.getAtivo());
        dto.setCategoriaId(produto.getCategoria().getId());

        return dto;
    }

    @Override
    public void apagarProduto(Integer id) {

        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        produtoRepository.deleteById(id);
    }
}
