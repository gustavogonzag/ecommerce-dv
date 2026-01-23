package com.gustavo.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gustavo.ecommerce.entity.enums.FormaPagamento;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Dados do cliente
    private String nomeCliente;
    private String telefone;
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "bairro_id")
    private Bairro bairro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ItemPedido> itens;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false, length = 20)
    private FormaPagamento formaPagamento;

    private BigDecimal subtotalProdutos;
    private BigDecimal taxaEntrega;
    private BigDecimal valorDesconto;
    private BigDecimal total;

    private LocalDateTime dataCriacao;

    public void setDataHora(LocalDateTime now) {
        this.dataCriacao = now;
    }

    public void setSubtotal(BigDecimal subtotalPedido) {
        this.subtotalProdutos = subtotalPedido;
    }

    public BigDecimal getSubtotal() {
        return subtotalProdutos;
    }

}
