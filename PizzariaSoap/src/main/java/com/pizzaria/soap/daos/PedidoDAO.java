package com.pizzaria.soap.daos;

import com.pizzaria.soap.models.Pedido;
import com.pizzaria.soap.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoDAO {

    // Método para salvar um pedido
    public Pedido salvar(Pedido pedido) {
        EntityManager em = ConexaoDAO.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pedido); // Salva o pedido no banco
            em.flush(); // Garante que o ID será gerado imediatamente
            em.getTransaction().commit();
            return pedido; // Retorna o pedido com ID gerado
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erro ao salvar pedido: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    // Método para buscar pedido por ID
    public Optional<Pedido> buscarPorId(int id) {
        EntityManager em = ConexaoDAO.getEntityManager();
        try {
            Pedido pedido = em.find(Pedido.class, id);
            return Optional.ofNullable(pedido);
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    // Método para buscar pedidos por CPF do cliente
    public List<Pedido> buscarPorCpf(String cpf) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Pedido> pedidos = new ArrayList<>();
        try {
            pedidos = em.createQuery("SELECT p FROM Pedido p WHERE p.cliente.cpf = :cpf", Pedido.class)
                    .setParameter("cpf", cpf)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar pedidos por CPF: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
        }
        return pedidos != null ? pedidos : new ArrayList<>();
    }

    // Método para listar todos os pedidos
    public List<Pedido> listarTodos() {
        EntityManager em = ConexaoDAO.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    // Método para atualizar o status de um pedido
    public void atualizarStatus(int pedidoId) {
        EntityManager em = ConexaoDAO.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Pedido pedido = em.find(Pedido.class, pedidoId);
            if (pedido == null) {
                throw new EntityNotFoundException("Pedido não encontrado com ID: " + pedidoId);
            }

            // Avançar o status do pedido
            pedido.avancarStatus();

            em.merge(pedido);
            transaction.commit();
        } catch (EntityNotFoundException e) {
            System.err.println(e.getMessage());
            throw e; // Lança a exceção para ser tratada em outro nível, se necessário
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("Erro ao atualizar status do pedido: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
