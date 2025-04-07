package com.pizzaria.soap.models;

public enum StatusPedido {
    RECEBIDO("Pedido recebido, aguardando preparo"),
    EM_PREPARACAO("Pedido em preparação"),
    SAIU_ENTREGA("Pedido saiu para entrega"),
    ENTREGUE("Pedido entregue");

    private final String descricao;

    // Construtor para adicionar uma descrição ao status
    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    // Getter para a descrição do status
    public String getDescricao() {
        return descricao;
    }

    // Método para converter uma string em um StatusPedido
    public static StatusPedido fromString(String status) {
        for (StatusPedido s : StatusPedido.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }

    // Método para obter o próximo status
    public StatusPedido proximoStatus() {
        switch (this) {
            case RECEBIDO:
                return EM_PREPARACAO;
            case EM_PREPARACAO:
                return SAIU_ENTREGA;
            case SAIU_ENTREGA:
                return ENTREGUE;
            default:
                throw new IllegalStateException("Não há próximo status para " + this);
        }
    }

    @Override
    public String toString() {
        return this.name() + " - " + descricao;
    }
}
