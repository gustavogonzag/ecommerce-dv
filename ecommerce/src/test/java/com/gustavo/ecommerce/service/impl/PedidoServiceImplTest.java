package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.ItemPedidoRequestDTO;
import com.gustavo.ecommerce.dto.request.PedidoRequestDTO;
import com.gustavo.ecommerce.dto.response.PageResponseDTO;
import com.gustavo.ecommerce.dto.response.PedidoResponseDTO;
import com.gustavo.ecommerce.entity.Bairro;
import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.Produto;
import com.gustavo.ecommerce.entity.enums.FormaPagamento;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import com.gustavo.ecommerce.exception.BusinessException;
import com.gustavo.ecommerce.exception.ResourceNotFoundException;
import com.gustavo.ecommerce.mapper.PedidoMapper;
import com.gustavo.ecommerce.repository.BairroRepository;
import com.gustavo.ecommerce.repository.PedidoRepository;
import com.gustavo.ecommerce.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private BairroRepository bairroRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoServiceImpl service;

    /* =======================
       CRIAR PEDIDO
       ======================= */

    @Test
    void deveCriarPedidoComSucesso() {
        // arrange
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setNomeCliente("Gustavo");
        dto.setTelefone("99999-9999");
        dto.setEndereco("Rua A");
        dto.setBairroId(1);
        dto.setFormaPagamento(FormaPagamento.valueOf("PIX"));

        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO();
        itemDTO.setProdutoId(10);
        itemDTO.setQuantidade(2);
        dto.setItens(List.of(itemDTO));

        Bairro bairro = new Bairro();
        bairro.setId(1);
        bairro.setTaxaEntrega(BigDecimal.valueOf(5));

        Produto produto = new Produto();
        produto.setId(10);
        produto.setPreco(BigDecimal.valueOf(20));

        when(bairroRepository.findById(1)).thenReturn(Optional.of(bairro));
        when(produtoRepository.findById(10)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        Pedido pedido = service.criarPedido(dto);

        // assert
        assertNotNull(pedido);
        assertEquals(StatusPedido.AGUARDANDO, pedido.getStatus());
        assertEquals(BigDecimal.valueOf(40), pedido.getSubtotal());
        assertEquals(BigDecimal.valueOf(45), pedido.getTotal());
        assertEquals(1, pedido.getItens().size());

        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    void deveLancarExcecaoAoCriarPedidoSemItens() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setItens(List.of());

        assertThrows(IllegalArgumentException.class,
                () -> service.criarPedido(dto));

        verifyNoInteractions(pedidoRepository);
    }

    @Test
    void deveLancarExcecaoQuandoBairroNaoExiste() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setBairroId(1);
        dto.setItens(List.of(new ItemPedidoRequestDTO()));

        when(bairroRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.criarPedido(dto));
    }

    /* =======================
       ATUALIZAR STATUS
       ======================= */

    @Test
    void deveAtualizarStatusPedidoComSucesso() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido atualizado = service.atualizarStatusPedido(1, StatusPedido.EM_PREPARO);

        assertEquals(StatusPedido.EM_PREPARO, atualizado.getStatus());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void naoDevePermitirAlterarPedidoEntregue() {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.ENTREGUE);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        assertThrows(BusinessException.class,
                () -> service.atualizarStatusPedido(1, StatusPedido.CANCELADO));
    }

    @Test
    void naoDevePermitirAlterarPedidoCancelado() {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.CANCELADO);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        assertThrows(BusinessException.class,
                () -> service.atualizarStatusPedido(1, StatusPedido.EM_PREPARO));
    }

    /* =======================
       BUSCAR PEDIDO
       ======================= */

    @Test
    void deveBuscarPedidoPorIdComSucesso() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        Pedido response = service.buscarPedidoPorId(1);

        assertEquals(1, response.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarPedidoInexistente() {
        when(pedidoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPedidoPorId(1));
    }

    /* =======================
       LISTAGENS
       ======================= */

    @Test
    void deveListarPedidosPaginados() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pedido> page = new PageImpl<>(List.of(new Pedido()));

        PageResponseDTO<PedidoResponseDTO> responseDTO = new PageResponseDTO<>();

        when(pedidoRepository.findAll((Example<Pedido>) any(), eq(pageable))).thenReturn(page);
        when(pedidoMapper.toPageResponse(page)).thenReturn(responseDTO);

        PageResponseDTO<PedidoResponseDTO> result =
                service.listarPedidosPaginados(null, null, null, pageable);

        assertNotNull(result);
        verify(pedidoMapper).toPageResponse(page);
    }

    @Test
    void deveListarPedidosAtrasados() {
        when(pedidoRepository.findPedidosAtrasados(any()))
                .thenReturn(List.of(new Pedido()));

        List<Pedido> pedidos = service.listarPedidosAtrasados(30);

        assertEquals(1, pedidos.size());
        verify(pedidoRepository).findPedidosAtrasados(any());
    }
}
