package com.gustavo.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

public class CategoriaRequestDTO {

    @NotNull
    private String nome;

    @NotNull
    private Integer id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
