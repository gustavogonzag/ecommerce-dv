package com.gustavo.ecommerce.repository;

import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByStatusOrderByDataCriacaoDesc(StatusPedido status);

    List<Pedido> findByDataCriacaoBetweenOrderByDataCriacaoDesc(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<Pedido> findByStatusAndDataCriacaoBetweenOrderByDataCriacaoDesc(
            StatusPedido status,
            LocalDateTime inicio,
            LocalDateTime fim
    );

}
