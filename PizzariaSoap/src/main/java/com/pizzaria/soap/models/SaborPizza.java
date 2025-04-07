package com.pizzaria.soap.models;

import java.math.BigDecimal;

public enum SaborPizza {
    MUSSARELA("estrogonofe", BigDecimal.valueOf(1.0)),
    CALABRESA("calabresa", BigDecimal.valueOf(2.0)),
    PORTUGUESA("portuguesa", BigDecimal.valueOf(3.0)),
    OUTRO("outro", BigDecimal.valueOf(1.0));

    private final String sabor;
    private final BigDecimal acrescimo;

    SaborPizza(String sabor, BigDecimal acrescimo) {
        this.sabor = sabor;
        this.acrescimo = acrescimo;
    }

    public String getSabor() {
        return sabor;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public static SaborPizza fromString(String sabor) {
        for (SaborPizza s : SaborPizza.values()) {
            if (s.sabor.equalsIgnoreCase(sabor)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Sabor inválido: " + sabor);
    }
}
