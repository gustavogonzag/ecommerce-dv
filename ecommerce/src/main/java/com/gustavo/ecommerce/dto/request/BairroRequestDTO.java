package com.gustavo.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class BairroRequestDTO {

    @NotNull
    private String nome;

    private Integer id;

    @NotNull
    private BigDecimal taxaEntrega;

    public BairroRequestDTO() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }
}
