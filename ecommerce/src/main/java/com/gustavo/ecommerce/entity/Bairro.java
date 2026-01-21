package com.gustavo.ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bairros")
public class Bairro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "taxa_entrega", nullable = false)
    private BigDecimal taxaEntrega;

    protected Bairro() {
    }

    public Bairro(String nome, BigDecimal taxaEntrega) {
        this.nome = nome;
        this.taxaEntrega = taxaEntrega;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }
}
