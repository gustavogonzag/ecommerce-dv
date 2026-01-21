package com.gustavo.ecommerce.repository;

import com.gustavo.ecommerce.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
