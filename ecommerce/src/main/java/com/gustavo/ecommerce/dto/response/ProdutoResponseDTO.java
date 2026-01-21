package com.gustavo.ecommerce.dto.response;

import java.math.BigDecimal;

public class ProdutoResponseDTO {

    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;

    public ProdutoResponseDTO(int id, String nome, String descricao, BigDecimal preco, String nome1) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
