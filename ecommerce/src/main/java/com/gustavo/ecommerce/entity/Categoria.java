package com.gustavo.ecommerce.entity;

import com.gustavo.ecommerce.dto.request.CategoriaRequestDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria extends CategoriaRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nome;

    public Categoria() {}

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
