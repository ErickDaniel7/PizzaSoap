package com.pizzaria.soap.models;

import java.math.BigDecimal;

public enum TamanhoPizza {
    P("P", BigDecimal.valueOf(20.0)),
    M("M", BigDecimal.valueOf(30.0)),
    G("G", BigDecimal.valueOf(40.0));

    private final String tamanho;
    private final BigDecimal precoBase;

    TamanhoPizza(String tamanho, BigDecimal precoBase) {
        this.tamanho = tamanho;
        this.precoBase = precoBase;
    }

    public String getTamanho() {
        return tamanho;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public static TamanhoPizza fromString(String tamanho) {
        for (TamanhoPizza t : TamanhoPizza.values()) {
            if (t.tamanho.equalsIgnoreCase(tamanho)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tamanho inválido: " + tamanho);
    }
}
