package com.pizzaria.soap.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Borda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING) // Usando Enum para armazenar sabor
    private SaborBorda sabor;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    // Construtor sem argumentos para o Hibernate
    public Borda() {
        // O construtor sem argumentos é necessário para o Hibernate funcionar corretamente.
    }

    // Construtor que define o sabor e o preço ao criar a borda
    public Borda(String sabor) {
        this.sabor = SaborBorda.fromString(sabor); // Usando Enum para buscar o sabor
        this.preco = this.sabor.getPreco(); // Preço relacionado ao sabor
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public SaborBorda getSabor() {
        return sabor;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSabor(String sabor) {
        this.sabor = SaborBorda.fromString(sabor); // Atualizando sabor com Enum
        this.preco = this.sabor.getPreco(); // Atualizando o preço de acordo com o sabor
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
