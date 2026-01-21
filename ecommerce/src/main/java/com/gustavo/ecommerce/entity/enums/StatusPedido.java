package com.gustavo.ecommerce.entity.enums;

public enum StatusPedido {

    ACEITO(1, "Aceito"),
    EM_PREPARO(2, "Em preparo"),
    SAIU_PARA_ENTREGA(3, "Saiu para entrega"),
    ENTREGUE(4, "Entregue"),
    CANCELADO(5, "Cancelado");

    private final int ordem;
    private final String descricao;

    StatusPedido(int ordem, String descricao) {
        this.ordem = ordem;
        this.descricao = descricao;
    }

    public int getOrdem() {
        return ordem;
    }

    public String getDescricao() {
        return descricao;
    }
}
