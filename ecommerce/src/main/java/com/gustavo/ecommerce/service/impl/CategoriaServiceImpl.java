package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.CategoriaRequestDTO;
import com.gustavo.ecommerce.entity.Categoria;
import com.gustavo.ecommerce.repository.CategoriaRepository;
import com.gustavo.ecommerce.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaServiceImpl(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoriaRequestDTO criarCategoria(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());

        Categoria salva = repository.save(categoria);

        CategoriaRequestDTO response = new CategoriaRequestDTO();
        response.setId(salva.getId());
        response.setNome(salva.getNome());

        return response;
    }

    @Override
    public CategoriaRequestDTO atualizarCategoria(CategoriaRequestDTO dto) {

        Categoria categoria = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoria.setNome(dto.getNome());

        Categoria atualizada = repository.save(categoria);

        CategoriaRequestDTO response = new CategoriaRequestDTO();
        response.setId(atualizada.getId());
        response.setNome(atualizada.getNome());

        return response;
    }

    @Override
    public List<CategoriaRequestDTO> listarCategorias() {

        return repository.findAll()
                .stream()
                .map(cat -> {
                    CategoriaRequestDTO dto = new CategoriaRequestDTO();
                    dto.setId(cat.getId());
                    dto.setNome(cat.getNome());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void apagarCategoria(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public CategoriaRequestDTO buscarCategoriaPorId(Integer id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        CategoriaRequestDTO dto = new CategoriaRequestDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());

        return dto;
    }
}
