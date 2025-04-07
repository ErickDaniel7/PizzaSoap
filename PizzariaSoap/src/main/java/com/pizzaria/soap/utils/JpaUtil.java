package com.pizzaria.soap.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JpaUtil {
    private static final EntityManagerFactory emf;
    private static final Logger logger = Logger.getLogger(JpaUtil.class.getName());

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

    public static void close() {
        if (emf != null && emf.isOpen()) {
            try {
                emf.close();
                logger.info("EntityManagerFactory fechado com sucesso.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao fechar o EntityManagerFactory: ", e);
            }
        }
    }

    // Método opcional para fechar o EMF quando a aplicação terminar (exemplo de uso)
    public static void shutdown() {
        close();
    }
}
