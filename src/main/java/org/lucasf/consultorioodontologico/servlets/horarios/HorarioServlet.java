package org.lucasf.consultorioodontologico.servlets.horarios;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.services.HorarioService;

import java.io.IOException;
import java.util.List;

@WebServlet("/horarios")
public class HorarioServlet extends HttpServlet {

    @Inject
    private HorarioService horarioService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("title", req.getAttribute("title") + ": Listado de horarios");

        // Recuperar y limpiar mensajes de la sesión
        HttpSession session = req.getSession();
        req.setAttribute("mensajeExito", session.getAttribute("mensajeExito"));
        req.setAttribute("mensajeError", session.getAttribute("mensajeError"));
        session.removeAttribute("mensajeExito");
        session.removeAttribute("mensajeError");

        try {
            List<Horario> horarios = horarioService.listar();
            req.setAttribute("horarios", horarios);
            getServletContext().getRequestDispatcher("/pages/horarios/listarHorarios.jsp").forward(req, resp);
        } catch (Exception e) {
            // Log del error (idealmente con algún logger)
            e.printStackTrace();
            // Establecer mensaje de error
            req.setAttribute("mensajeError", "Error al cargar los horarios");
            req.getRequestDispatcher("/pages/horarios/listarHorarios.jsp").forward(req, resp);
        }
    }
}
