package org.lucasf.consultorioodontologico.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;

import java.lang.reflect.Method;
import java.util.logging.Logger;

@TransactionalJpa
@Interceptor
public class TransactionalJpaInterceptor {

    @Inject
    private EntityManager em;

    @Inject
    private Logger log;

    @AroundInvoke
    public Object transactional(InvocationContext invocationContext) throws Exception {
        Method metodo = invocationContext.getMethod();
        if (metodo.isAnnotationPresent(NoTransactional.class)) {
            return invocationContext.proceed(); // no abrir transacción
        }

        boolean isTransactionOwner = false;
        try {
            // Verificar si ya hay una transacción activa
            if (!em.getTransaction().isActive()) {
                log.info(" -----> Iniciando transaccion " + metodo.getName() +
                        " de la clase " + metodo.getDeclaringClass());
                em.getTransaction().begin();
                isTransactionOwner = true;
            } else {
                log.info(" -----> Usando transaccion existente para " + metodo.getName());
            }

            Object resultado = invocationContext.proceed();

            if (isTransactionOwner) {
                em.getTransaction().commit();
                log.info(" -----> Realizando commit y finalizando transaccion " + metodo.getName() +
                        " de la clase " + metodo.getDeclaringClass());
            }

            return resultado;
        } catch (Exception e) {
            if (isTransactionOwner && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                log.warning(" -----> Rollback realizado para " + metodo.getName());
            } else if (!isTransactionOwner) {
                log.warning(" -----> No se puede hacer rollback porque no es propietario de la transacción");
            }

            log.severe(" -----> Error en la transaccion " + metodo.getName() +
                    " de la clase " + metodo.getDeclaringClass());
            throw e;
        }
    }
}