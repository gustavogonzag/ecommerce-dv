package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.CategoriaRequestDTO;
import com.gustavo.ecommerce.entity.Categoria;
import com.gustavo.ecommerce.exception.ResourceNotFoundException;
import com.gustavo.ecommerce.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaServiceImpl service;

    @Test
    void deveCriarCategoriaComSucesso() {
        // arrange
        CategoriaRequestDTO dto = new CategoriaRequestDTO();
        dto.setNome("Bebidas");

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setId(1);
        categoriaSalva.setNome("Bebidas");

        when(repository.save(any(Categoria.class))).thenReturn(categoriaSalva);

        // act
        CategoriaRequestDTO response = service.criarCategoria(dto);

        // assert
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Bebidas", response.getNome());

        verify(repository, times(1)).save(any(Categoria.class));
    }

    @Test
    void deveAtualizarCategoriaComSucesso() {
        // arrange
        CategoriaRequestDTO dto = new CategoriaRequestDTO();
        dto.setId(1);
        dto.setNome("Lanches");

        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(1);
        categoriaExistente.setNome("Antigo");

        when(repository.findById(1)).thenReturn(Optional.of(categoriaExistente));
        when(repository.save(any(Categoria.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        CategoriaRequestDTO response = service.atualizarCategoria(dto);

        // assert
        assertEquals(1, response.getId());
        assertEquals("Lanches", response.getNome());

        verify(repository).findById(1);
        verify(repository).save(categoriaExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaInexistente() {
        // arrange
        CategoriaRequestDTO dto = new CategoriaRequestDTO();
        dto.setId(99);
        dto.setNome("Teste");

        when(repository.findById(99)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizarCategoria(dto));

        verify(repository).findById(99);
        verify(repository, never()).save(any());
    }

    @Test
    void deveListarCategoriasComSucesso() {
        // arrange
        Categoria c1 = new Categoria();
        c1.setId(1);
        c1.setNome("Bebidas");

        Categoria c2 = new Categoria();
        c2.setId(2);
        c2.setNome("Lanches");

        when(repository.findAll()).thenReturn(List.of(c1, c2));

        // act
        List<CategoriaRequestDTO> lista = service.listarCategorias();

        // assert
        assertEquals(2, lista.size());
        assertEquals("Bebidas", lista.get(0).getNome());
        assertEquals("Lanches", lista.get(1).getNome());

        verify(repository).findAll();
    }

    @Test
    void deveApagarCategoriaComSucesso() {
        // arrange
        when(repository.existsById(1)).thenReturn(true);

        // act
        service.apagarCategoria(1);

        // assert
        verify(repository).deleteById(1);
    }

    @Test
    void deveLancarExcecaoAoApagarCategoriaInexistente() {
        // arrange
        when(repository.existsById(1)).thenReturn(false);

        // act + assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.apagarCategoria(1));

        verify(repository, never()).deleteById(anyInt());
    }

    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        // arrange
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Sobremesas");

        when(repository.findById(1)).thenReturn(Optional.of(categoria));

        // act
        CategoriaRequestDTO response = service.buscarCategoriaPorId(1);

        // assert
        assertEquals(1, response.getId());
        assertEquals("Sobremesas", response.getNome());

        verify(repository).findById(1);
    }

    @Test
    void deveLancarExcecaoAoBuscarCategoriaInexistente() {
        // arrange
        when(repository.findById(1)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarCategoriaPorId(1));

        verify(repository).findById(1);
    }
}
