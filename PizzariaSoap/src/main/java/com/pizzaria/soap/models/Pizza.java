package com.pizzaria.soap.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String sabor;
    private String tamanho;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    public Pizza() {}

    public Pizza(String sabor, String tamanho) {
        this.sabor = sabor;
        this.tamanho = tamanho;
        this.preco = calcularPreco(sabor, tamanho);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getSabor() {
        return sabor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
        this.preco = calcularPreco(sabor, this.tamanho); // Atualiza o preço ao mudar o sabor
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
        this.preco = calcularPreco(this.sabor, tamanho); // Atualiza o preço ao mudar o tamanho
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    // Calcular o preço da pizza com base no sabor e no tamanho
    private BigDecimal calcularPreco(String sabor, String tamanho) {
        TamanhoPizza tamanhoPizza = TamanhoPizza.fromString(tamanho);
        SaborPizza saborPizza = SaborPizza.fromString(sabor);

        return tamanhoPizza.getPrecoBase().add(saborPizza.getAcrescimo());
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", sabor='" + sabor + '\'' +
                ", tamanho='" + tamanho + '\'' +
                ", preco=" + preco +
                '}';
    }
}
