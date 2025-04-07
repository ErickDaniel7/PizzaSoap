package com.pizzaria.soap.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoDAO {
    private static final EntityManagerFactory emf;
    private static final Logger logger = Logger.getLogger(ConexaoDAO.class.getName());

    static {
        try {
            emf = Persistence.createEntityManagerFactory("PizzariaSoap");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar o EntityManagerFactory: ", e);
            throw new ExceptionInInitializerError("Erro ao inicializar a fábrica de persistência.");
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Fechamento do EntityManagerFactory quando a aplicação terminar
    public static void fechar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            logger.info("EntityManagerFactory fechado com sucesso.");
        }
    }
}
