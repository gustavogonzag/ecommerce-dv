package com.gustavo.ecommerce.repository.specification;

import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PedidoSpecification {

    public static Specification<Pedido> comStatus(StatusPedido status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Pedido> dataInicio(LocalDate dataInicio) {
        return (root, query, cb) -> {
            if (dataInicio == null) return null;
            return cb.greaterThanOrEqualTo(
                    root.get("dataCriacao"),
                    dataInicio.atStartOfDay()
            );
        };
    }

    public static Specification<Pedido> dataFim(LocalDate dataFim) {
        return (root, query, cb) -> {
            if (dataFim == null) return null;
            return cb.lessThanOrEqualTo(
                    root.get("dataCriacao"),
                    dataFim.atTime(23, 59, 59)
            );
        };
    }
}
