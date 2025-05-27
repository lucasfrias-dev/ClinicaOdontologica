package org.lucasf.consultorioodontologico.servlets.secretarios;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Secretario;
import org.lucasf.consultorioodontologico.services.OdontologoService;
import org.lucasf.consultorioodontologico.services.SecretarioService;

import java.io.IOException;
import java.util.List;

@WebServlet("/secretarios")
public class SecretarioServlet extends HttpServlet {

    @Inject
    private SecretarioService secretarioService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("title", req.getAttribute("title") + ": Listado de secretarios");

        try {
            List<Secretario> secretarios = secretarioService.listar();
            req.setAttribute("secretarios", secretarios);
            getServletContext().getRequestDispatcher("/pages/secretarios/listarSecretarios.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
