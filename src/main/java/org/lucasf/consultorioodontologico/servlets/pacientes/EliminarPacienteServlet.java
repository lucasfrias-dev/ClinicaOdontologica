package org.lucasf.consultorioodontologico.servlets.pacientes;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.services.PacienteService;

import java.io.IOException;

@WebServlet("/pacientes/eliminar")
public class EliminarPacienteServlet extends HttpServlet {

    @Inject
    private PacienteService pacienteService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        try {
            Long idTurno = Long.valueOf(req.getParameter("idPaciente"));
            pacienteService.eliminar(idTurno);
            session.setAttribute("mensajeExito", "Paciente eliminado correctamente.");
        } catch (NumberFormatException e) {
            session.setAttribute("mensajeError", "ID de paciente inválido");
        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", "El paciente no existe o ya fue eliminado");
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Error al eliminar paciente: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/pacientes");
    }
}
