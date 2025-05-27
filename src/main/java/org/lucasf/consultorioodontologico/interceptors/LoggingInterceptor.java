package org.lucasf.consultorioodontologico.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.logging.Logger;

@Logging
@Interceptor
public class LoggingInterceptor {

    @Inject
    private Logger log;

    public Object logging(InvocationContext invocationContext) throws Exception {
        log.info(" ***** Entrando al metodo: " + invocationContext.getMethod().getName() +
                " de la clase " + invocationContext.getMethod().getDeclaringClass().getName());

        Object resultado = invocationContext.proceed();

        log.info(" ***** Saliendo de la invocacion al metodo: " + invocationContext.getMethod().getName());

        return resultado;
    }
}