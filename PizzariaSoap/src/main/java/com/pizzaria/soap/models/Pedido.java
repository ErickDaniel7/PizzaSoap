package com.pizzaria.soap.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.RECEBIDO; // Status inicial

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // Removendo o Optional e usando Cliente diretamente

    @Column(precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    private String observacoes;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItensPedido> itens = new ArrayList<>();

    // Getters
    public Long getId() {
        return id; // O ID já é Long, não é necessário convertê-lo
    }

    public Cliente getCliente() {
        return cliente; // Retorna Cliente diretamente, sem Optional
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<ItensPedido> getItens() {
        return itens;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }
        this.cliente = cliente;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public void setItens(List<ItensPedido> itens) {
        this.itens = itens;
        calcularValorTotal();
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    // Métodos para adicionar e remover itens do pedido
    public void adicionarItem(ItensPedido itemPedido) {
        this.itens.add(itemPedido);
        itemPedido.setPedido(this);
        calcularValorTotal();
    }

    public void removerItem(ItensPedido itemPedido) {
        this.itens.remove(itemPedido);
        calcularValorTotal();
    }

    // Calcular o valor total com base nos itens do pedido
    @PrePersist
    @PreUpdate
    public void calcularValorTotal() {
        this.valorTotal = itens.stream()
                .map(ItensPedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Avançar o status do pedido
    public void avancarStatus() {
        switch (this.status) {
            case RECEBIDO:
                this.status = StatusPedido.EM_PREPARACAO;
                break;
            case EM_PREPARACAO:
                this.status = StatusPedido.SAIU_ENTREGA;
                break;
            case SAIU_ENTREGA:
                this.status = StatusPedido.ENTREGUE;
                break;
            default:
                throw new IllegalStateException("Status do pedido não pode avançar.");
        }
    }

    // Sobrescreve o método toString para uma visualização mais legível
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", status=" + status +
                ", cliente=" + (cliente != null ? cliente.getNome() : "N/A") + // Verifica se o cliente está presente
                ", valorTotal=" + valorTotal +
                ", observacoes='" + observacoes + '\'' +
                ", itens=" + itens.size() +
                '}';
    }
}
