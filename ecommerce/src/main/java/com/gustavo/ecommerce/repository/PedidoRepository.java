package com.gustavo.ecommerce.repository;

import com.gustavo.ecommerce.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
