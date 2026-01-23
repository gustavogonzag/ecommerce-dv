package com.gustavo.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoRequestDTO {

    @NotBlank
    private String nomeCliente;

    @NotBlank
    private String telefone;

    @NotBlank
    private String endereco;

    @NotNull
    private Integer bairroId;

    @NotNull
    private List<ItemPedidoRequestDTO> itens;

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getBairroId() {
        return bairroId;
    }

    public void setBairroId(Integer bairroId) {
        this.bairroId = bairroId;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }
}
