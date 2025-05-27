package org.lucasf.consultorioodontologico.listeners;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.lucasf.consultorioodontologico.models.Rol;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.services.UsuarioService;
import org.lucasf.consultorioodontologico.utils.PasswordEncoder;

@WebListener
public class AppListener implements ServletContextListener,
        ServletRequestListener, HttpSessionListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        servletContext.log("Inicializando la aplicacion!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext.log("Destruyendo la aplicacion!");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        servletContext.log("Inicializando el request!");
        sre.getServletRequest().setAttribute("title", "Clínica Odontológica");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        servletContext.log("Destruyendo el request!");
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        servletContext.log("Destruyendo la sesion http");
    }
}
