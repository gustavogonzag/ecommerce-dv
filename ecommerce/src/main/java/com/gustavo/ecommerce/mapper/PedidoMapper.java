package com.gustavo.ecommerce.mapper;

import com.gustavo.ecommerce.dto.response.ItemPedidoResponseDTO;
import com.gustavo.ecommerce.dto.response.PageResponseDTO;
import com.gustavo.ecommerce.dto.response.PedidoResponseDTO;
import com.gustavo.ecommerce.entity.ItemPedido;
import com.gustavo.ecommerce.entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    @Autowired
    private com.gustavo.ecommerce.Business.PedidoAtrasoService pedidoAtrasoService;


    public PedidoResponseDTO toResponseDTO(Pedido pedido) {

        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setNomeCliente(pedido.getNomeCliente());
        dto.setTelefone(pedido.getTelefone());
        dto.setEndereco(pedido.getEndereco());

        dto.setStatus(pedido.getStatus());
        dto.setAtrasado(pedidoAtrasoService.isPedidoAtrasado(pedido));
        dto.setFormaPagamento(pedido.getFormaPagamento());

        dto.setSubtotalProdutos(pedido.getSubtotal());
        dto.setTaxaEntrega(pedido.getTaxaEntrega());
        dto.setTotal(pedido.getTotal());

        dto.setDataCriacao(pedido.getDataCriacao());

        if (pedido.getBairro() != null) {
            dto.setBairroId(pedido.getBairro().getId());
            dto.setBairroNome(pedido.getBairro().getNome());
        }

        List<ItemPedidoResponseDTO> itens = pedido.getItens()
                .stream()
                .map(this::toItemResponseDTO)
                .toList();

        dto.setItens(itens);

        return dto;
    }

    private ItemPedidoResponseDTO toItemResponseDTO(ItemPedido item) {
        ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();
        dto.setProdutoId(item.getProduto().getId());
        dto.setNomeProduto(item.getProduto().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }

    public PageResponseDTO<PedidoResponseDTO> toPageResponse(Page<Pedido> page) {

        List<PedidoResponseDTO> content = page.getContent()
                .stream()
                .map(this::toResponseDTO)
                .toList();

        return new PageResponseDTO<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
