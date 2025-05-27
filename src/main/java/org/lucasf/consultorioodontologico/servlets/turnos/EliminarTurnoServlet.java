package org.lucasf.consultorioodontologico.servlets.turnos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.entities.Turno;
import org.lucasf.consultorioodontologico.services.TurnoService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/turnos/eliminar")
public class EliminarTurnoServlet extends HttpServlet {

    @Inject
    private TurnoService turnoService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        try {
            Long idTurno = Long.valueOf(req.getParameter("idTurno"));
            turnoService.eliminar(idTurno);
            session.setAttribute("mensajeExito", "Turno eliminado correctamente.");
        } catch (NumberFormatException e) {
            session.setAttribute("mensajeError", "ID de turno inválido");
        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", "El turno no existe o ya fue eliminado");
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Error al eliminar turno: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/turnos");
    }
}
