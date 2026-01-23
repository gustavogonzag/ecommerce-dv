package com.gustavo.ecommerce.entity.enums;

public enum StatusPedido {

    AGUARDANDO(1, "Aguardando"),
    ACEITO(2, "Aceito"),
    EM_PREPARO(3, "Em preparo"),
    SAIU_PARA_ENTREGA(4, "Saiu para entrega"),
    ENTREGUE(5, "Entregue"),
    CANCELADO(6, "Cancelado");

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
