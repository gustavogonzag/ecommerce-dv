package com.gustavo.ecommerce.dto.request;

import com.gustavo.ecommerce.entity.enums.FormaPagamento;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoRequestDTO {

    private Integer id;

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

    private BigDecimal subtotalProdutos;

    private BigDecimal taxaEntrega;

    private BigDecimal valorDesconto;

    private BigDecimal valorTotal;

    private Integer cupom;

    private StatusPedido status;

    private FormaPagamento formaPagamento;

    private LocalDate dataCriacao;

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

    public BigDecimal getSubtotalProdutos() {
        return subtotalProdutos;
    }

    public void setSubtotalProdutos(BigDecimal subtotalProdutos) {
        this.subtotalProdutos = subtotalProdutos;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getCupom() {
        return cupom;
    }

    public void setCupom(Integer cupom) {
        this.cupom = cupom;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
