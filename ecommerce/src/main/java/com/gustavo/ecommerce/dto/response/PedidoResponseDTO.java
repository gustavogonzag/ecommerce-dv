package com.gustavo.ecommerce.dto.response;

import com.gustavo.ecommerce.entity.enums.FormaPagamento;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PedidoResponseDTO {

    private Integer id;
    private String nomeCliente;
    private String telefone;
    private String endereco;

    private Integer bairroId;
    private String bairroNome;

    private StatusPedido status;
    private boolean atrasado;
    private FormaPagamento formaPagamento;

    private BigDecimal subtotalProdutos;
    private BigDecimal taxaEntrega;
    private BigDecimal total;

    private LocalDateTime dataCriacao;

    private List<ItemPedidoResponseDTO> itens;

}
