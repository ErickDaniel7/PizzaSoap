package com.pizzaria.soap.daos;

import com.pizzaria.soap.models.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Optional;

public class ClienteDAO {

    // Método para salvar um cliente
    public void salvar(Cliente cliente) {
        EntityManager em = ConexaoDAO.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
            e.printStackTrace();
            // Considerar lançar uma exceção personalizada ou rethrow
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // Método para buscar cliente por ID
    public Optional<Cliente> buscarPorId(int id) {
        EntityManager em = ConexaoDAO.getEntityManager();
        try {
            Cliente cliente = em.find(Cliente.class, id);
            return Optional.ofNullable(cliente);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // Método para buscar cliente por CPF
    public Optional<Cliente> buscarPorCpf(String cpf) {
        EntityManager em = ConexaoDAO.getEntityManager();
        try {
            List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class)
                    .setParameter("cpf", cpf)
                    .getResultList();
            if (clientes.isEmpty()) {
                return null; // Nenhum cliente encontrado
            } else if (clientes.size() == 1) {
                return Optional.ofNullable(clientes.get(0)); // Retorna o único cliente encontrado
            } else {
                // Lidar com múltiplos resultados, por exemplo, escolher o primeiro ou lançar exceção
                System.out.println("Warning: Múltiplos clientes encontrados com o mesmo CPF.");
                return Optional.ofNullable(clientes.get(0)); // Retorna o primeiro cliente encontrado, ou você pode lançar uma exceção personalizada
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    }

