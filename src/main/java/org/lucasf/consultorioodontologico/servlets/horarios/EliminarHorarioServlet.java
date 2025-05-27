package org.lucasf.consultorioodontologico.servlets.horarios;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.services.HorarioService;

import java.io.IOException;

@WebServlet("/horarios/eliminar")
public class EliminarHorarioServlet extends HttpServlet {

    @Inject
    private HorarioService horarioService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        try {
            Long idHorario = Long.valueOf(req.getParameter("idHorario"));
            horarioService.eliminar(idHorario);
            session.setAttribute("mensajeExito", "Horario eliminado correctamente.");
        } catch (NumberFormatException e) {
            session.setAttribute("mensajeError", "ID de horario inválido");
        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", "El horario no existe o ya fue eliminado");
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Error al eliminar horario: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/horarios");
    }
}
