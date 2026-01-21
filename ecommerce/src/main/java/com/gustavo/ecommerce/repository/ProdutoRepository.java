package com.gustavo.ecommerce.repository;

import com.gustavo.ecommerce.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
