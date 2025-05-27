package org.lucasf.consultorioodontologico.configs;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lucasf.consultorioodontologico.utils.JpaUtil;
import java.util.logging.Logger;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ProducerResources {

    // ⚠️ Eliminamos esta inyección que causaba el ciclo
    // @Inject
    // private Logger log;

    // ✅ Logger como productor puro, sin depender de sí mismo
    @Produces
    public static Logger beanLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    // ✅ Producción de EntityManager para cada request
    @Produces
    @RequestScoped
    public EntityManager beanEntityManager() {
        return JpaUtil.getEntityManger();
    }

    // ✅ Cierre del EntityManager, usando un logger local seguro
    public void close(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
            Logger logger = Logger.getLogger(getClass().getName());
            logger.info("Cerrando la conexión del EntityManager");
        }
    }
}

