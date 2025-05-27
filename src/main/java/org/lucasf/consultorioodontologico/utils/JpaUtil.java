package org.lucasf.consultorioodontologico.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("ConsultorioOdontologico_PU");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Fallo al crear EntityManagerFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManger(){
        return entityManagerFactory.createEntityManager();
    }

}
