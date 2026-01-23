package com.gustavo.ecommerce.entity.enums;

public enum FormaPagamento {

    DINHEIRO(1, "DINHEIRO"),
    DEBITO(2, "DEBITO"),
    CREDITO(3, "CREDITO"),
    PIX(4, "PIX");

    private final int codigo;
    private final String descricao;

    FormaPagamento(int codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
