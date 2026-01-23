package com.gustavo.ecommerce.repository;

import com.gustavo.ecommerce.entity.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BairroRepository extends JpaRepository<Bairro, Integer> {
}
