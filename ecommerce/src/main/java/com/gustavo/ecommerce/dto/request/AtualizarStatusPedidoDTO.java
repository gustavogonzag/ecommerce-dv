package com.gustavo.ecommerce.dto.request;

import com.gustavo.ecommerce.entity.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

public class AtualizarStatusPedidoDTO {

    @NotNull
    private StatusPedido statusPedido;

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
}
