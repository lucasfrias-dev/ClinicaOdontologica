package org.lucasf.consultorioodontologico.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.Rol;
import org.lucasf.consultorioodontologico.models.entities.Usuario;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI();
        HttpSession session = req.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        // Permitir recursos estáticos y páginas públicas (login, css, js, imágenes, etc)
        if (path.contains("/login") || path.contains("/css") || path.contains("/js") || path.contains("/images")) {
            chain.doFilter(request, response);
            return;
        }

        // Si no hay usuario logueado, redirigir al login
        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Rol rol = usuario.getRol();

        // ADMIN: acceso total (sin restricciones)
        if (rol == Rol.ADMIN) {
            chain.doFilter(request, response);
            return;
        }

        if (rol == Rol.SECRETARIO) {
            // Rutas restringidas específicas para SECRETARIO
            if (path.startsWith(req.getContextPath() + "/odontologos/registrar") ||
                    path.startsWith(req.getContextPath() + "/secretarios/registrar") ||
                    path.contains("/editar") ||
                    path.contains("/eliminar") ||
                    path.startsWith(req.getContextPath() + "/horarios/asignar")) {

                resp.sendRedirect(req.getContextPath() + "/accesoDenegado.jsp");
                return;
            }

            // Todo lo demás está permitido
            chain.doFilter(request, response);
            return;
        }

        if (rol == Rol.ODONTOLOGO) {
            // Rutas restringidas específicas para SECRETARIO
            if (path.contains("/editar") ||
                    path.contains("/eliminar") ||
                    path.contains("/registrar") ||
                    path.contains("/asignar")) {

                resp.sendRedirect(req.getContextPath() + "/accesoDenegado.jsp");
                return;
            }

            // Todo lo demás está permitido
            chain.doFilter(request, response);
            return;
        }

        // Si llega aquí, denegar acceso por defecto
        resp.sendRedirect(req.getContextPath() + "/accesoDenegado.jsp");
    }
}

