package com.pizzaria.soap.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

@WebService
public class PedidoService {

    // Método para fazer um pedido, agora retornando informações mais detalhadas
    @WebMethod
    public Pedido fazerPedido(@WebParam(name = "nomePizza") String nomePizza) {
        if (nomePizza == null || nomePizza.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da pizza não pode ser vazio.");
        }

        // Criação do pedido
        Pedido pedido = new Pedido();
        pedido.setNomePizza(nomePizza);
        pedido.setStatus("Pedido Recebido");

        // Simulação do processamento do pedido (poderia ser integrado com o banco de dados)
        System.out.println("Pedido recebido para pizza de: " + nomePizza);

        return pedido;
    }

    // Método para consultar o status de um pedido
    @WebMethod
    public String consultarStatus(@WebParam(name = "idPedido") Long idPedido) {
        // Aqui você pode implementar a lógica para buscar o status do pedido no banco de dados
        // Neste exemplo, vamos apenas retornar um status simulado
        return "Status do pedido " + idPedido + ": Em preparação";
    }

    // Classe Pedido - representando um pedido mais detalhado
    public static class Pedido {
        private String nomePizza;
        private String status;

        // Getters e Setters
        public String getNomePizza() {
            return nomePizza;
        }

        public void setNomePizza(String nomePizza) {
            this.nomePizza = nomePizza;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Pedido{" +
                    "nomePizza='" + nomePizza + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
