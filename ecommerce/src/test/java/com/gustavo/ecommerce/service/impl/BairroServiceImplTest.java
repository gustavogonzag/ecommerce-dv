package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.BairroRequestDTO;
import com.gustavo.ecommerce.entity.Bairro;
import com.gustavo.ecommerce.exception.ResourceNotFoundException;
import com.gustavo.ecommerce.repository.BairroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BairroServiceImplTest {

    @Mock
    private BairroRepository bairroRepository;

    @InjectMocks
    private BairroServiceImpl bairroService;

    @Test
    void deveCadastrarBairroComSucesso() {
        // arrange
        BairroRequestDTO dto = new BairroRequestDTO();
        dto.setNome("Centro");
        dto.setTaxaEntrega(BigDecimal.valueOf(5.00));

        Bairro bairroSalvo = new Bairro();
        bairroSalvo.setId(1);
        bairroSalvo.setNome("Centro");
        bairroSalvo.setTaxaEntrega(BigDecimal.valueOf(5.00));

        when(bairroRepository.save(any(Bairro.class))).thenReturn(bairroSalvo);

        // act
        BairroRequestDTO response = bairroService.cadastroBairro(dto);

        // assert
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Centro", response.getNome());
        assertEquals(BigDecimal.valueOf(5.00), response.getTaxaEntrega());

        verify(bairroRepository, times(1)).save(any(Bairro.class));
    }

    @Test
    void deveAtualizarBairroComSucesso() {
        // arrange
        BairroRequestDTO dto = new BairroRequestDTO();
        dto.setId(1);
        dto.setNome("Bairro Novo");
        dto.setTaxaEntrega(BigDecimal.valueOf(7.50));

        Bairro bairroExistente = new Bairro();
        bairroExistente.setId(1);
        bairroExistente.setNome("Antigo");
        bairroExistente.setTaxaEntrega(BigDecimal.valueOf(4.00));

        when(bairroRepository.findById(1)).thenReturn(Optional.of(bairroExistente));
        when(bairroRepository.save(any(Bairro.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        BairroRequestDTO response = bairroService.atualizarBairro(dto);

        // assert
        assertEquals("Bairro Novo", response.getNome());
        assertEquals(BigDecimal.valueOf(7.50), response.getTaxaEntrega());

        verify(bairroRepository).findById(1);
        verify(bairroRepository).save(bairroExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarBairroInexistente() {
        // arrange
        BairroRequestDTO dto = new BairroRequestDTO();
        dto.setId(99);

        when(bairroRepository.findById(99)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(ResourceNotFoundException.class,
                () -> bairroService.atualizarBairro(dto));

        verify(bairroRepository).findById(99);
        verify(bairroRepository, never()).save(any());
    }

    @Test
    void deveListarBairrosComSucesso() {
        // arrange
        Bairro b1 = new Bairro();
        b1.setId(1);
        b1.setNome("Centro");
        b1.setTaxaEntrega(BigDecimal.valueOf(5.00));

        Bairro b2 = new Bairro();
        b2.setId(2);
        b2.setNome("Zona Sul");
        b2.setTaxaEntrega(BigDecimal.valueOf(8.00));

        when(bairroRepository.findAll()).thenReturn(List.of(b1, b2));

        // act
        List<BairroRequestDTO> lista = bairroService.listarBairros();

        // assert
        assertEquals(2, lista.size());
        assertEquals("Centro", lista.get(0).getNome());
        assertEquals("Zona Sul", lista.get(1).getNome());

        verify(bairroRepository).findAll();
    }

    @Test
    void deveApagarBairroComSucesso() {
        // arrange
        when(bairroRepository.existsById(1)).thenReturn(true);

        // act
        bairroService.apagarBairro(1);

        // assert
        verify(bairroRepository).deleteById(1);
    }

    @Test
    void deveLancarExcecaoAoApagarBairroInexistente() {
        // arrange
        when(bairroRepository.existsById(1)).thenReturn(false);

        // act + assert
        assertThrows(ResourceNotFoundException.class,
                () -> bairroService.apagarBairro(1));

        verify(bairroRepository, never()).deleteById(anyInt());
    }
}
