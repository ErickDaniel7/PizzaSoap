package com.pizzaria.soap.services;

import com.pizzaria.soap.daos.PedidoDAO;
import com.pizzaria.soap.models.Pedido;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// No AtualizadorPedidos
public class AtualizadorPedidos {
    private static final PedidoDAO pedidoDAO = new PedidoDAO();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Intervalo de tempo em segundos (configurável)
    private static final long INTERVALO = 60; // 60 segundos

    public static void iniciarAtualizacaoAutomatica() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List<Pedido> pedidos = pedidoDAO.listarTodos();
                    for (Pedido pedido : pedidos) {
                        pedidoDAO.atualizarStatus(pedido.getId().intValue()); // Convertendo Long para int
                    }
                    System.out.println("Status dos pedidos atualizados.");
                } catch (Exception e) {
                    System.err.println("Erro ao atualizar status dos pedidos: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        // Agendar a execução da tarefa a cada INTERVALO segundos
        scheduler.scheduleAtFixedRate(task, 0, INTERVALO, TimeUnit.SECONDS);
    }

    // Método para encerrar o agendador, se necessário
    public static void pararAtualizacaoAutomatica() {
        scheduler.shutdown();
    }
}
