package com.pizzaria.soap.models;

import java.math.BigDecimal;

public enum SaborBorda {
    CHEDDAR("cheddar", BigDecimal.valueOf(5.0)),
    CATUPIRY("catupiry", BigDecimal.valueOf(6.0)),
    CHOCOLATE("chocolate", BigDecimal.valueOf(4.5)),
    OUTRO("outro", BigDecimal.valueOf(4.0)),
    BACON("bacon", BigDecimal.valueOf(7.0)),
    FRANGO_COM_CATUPIRY("frango com catupiry", BigDecimal.valueOf(6.5)),
    CALABRESA("calabresa", BigDecimal.valueOf(6.0)),
    MEXICANA("mexicana", BigDecimal.valueOf(6.5)),
    FUNGHI("funghi", BigDecimal.valueOf(7.0));

    private final String sabor;
    private final BigDecimal preco;

    // Construtor do Enum
    SaborBorda(String sabor, BigDecimal preco) {
        this.sabor = sabor;
        this.preco = preco;
    }

    // Métodos getters
    public String getSabor() {
        return sabor;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    // Método que retorna o sabor a partir de uma String
    public static SaborBorda fromString(String sabor) {
        for (SaborBorda sb : SaborBorda.values()) {
            if (sb.sabor.equalsIgnoreCase(sabor)) {
                return sb;
            }
        }
        return OUTRO; // Valor default caso o sabor não seja encontrado
    }
}
