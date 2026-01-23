package com.gustavo.ecommerce.repository;

import com.gustavo.ecommerce.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>, JpaSpecificationExecutor<Pedido> {

    Page<Pedido> findAllByOrderByDataCriacaoDesc(Pageable pageable);

    @Query("""
        SELECT p FROM Pedido p
        WHERE p.status NOT IN ('ENTREGUE', 'CANCELADO')
          AND p.dataCriacao <= :limite
    """)
    List<Pedido> findPedidosAtrasados(@Param("limite") LocalDateTime limite);

}
