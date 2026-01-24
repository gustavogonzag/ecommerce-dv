package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.ProdutoRequestDTO;
import com.gustavo.ecommerce.entity.Categoria;
import com.gustavo.ecommerce.entity.Produto;
import com.gustavo.ecommerce.repository.CategoriaRepository;
import com.gustavo.ecommerce.repository.ProdutoRepository;
import com.gustavo.ecommerce.service.impl.ProdutoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Test
    void deveCriarProdutoComSucesso() {
        Categoria categoria = new Categoria();
        categoria.setId(1);

        Produto produtoSalvo = new Produto();
        produtoSalvo.setId(10);
        produtoSalvo.setNome("Notebook");
        produtoSalvo.setDescricao("Notebook Gamer");
        produtoSalvo.setPreco(BigDecimal.valueOf(5000));
        produtoSalvo.setAtivo(true);
        produtoSalvo.setCategoria(categoria);

        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setNome("Notebook");
        request.setDescricao("Notebook Gamer");
        request.setPreco(BigDecimal.valueOf(5000));
        request.setAtivo(true);
        request.setCategoriaId(1);

        when(categoriaRepository.findById(1))
                .thenReturn(Optional.of(categoria));

        when(produtoRepository.save(any(Produto.class)))
                .thenReturn(produtoSalvo);

        ProdutoRequestDTO response = produtoService.criar(request);

        assertNotNull(response);
        assertEquals(10, response.getId());
        assertEquals("Notebook", response.getNome());
        assertEquals(1, response.getCategoriaId());
    }

    @Test
    void deveLancarErroQuandoCategoriaNaoExiste() {
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setCategoriaId(99);

        when(categoriaRepository.findById(99))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> produtoService.criar(request)
        );

        assertEquals("Categoria não encontrada", exception.getMessage());
    }



}
