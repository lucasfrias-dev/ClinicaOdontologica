package org.lucasf.consultorioodontologico.servlets.odontologos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.services.OdontologoService;

import java.io.IOException;

@WebServlet("/odontologos/eliminar")
public class EliminarOdontologoServlet extends HttpServlet {

    @Inject
    private OdontologoService odontologoService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        try {
            Long idOdontologo = Long.valueOf(req.getParameter("idOdontologo"));
            odontologoService.eliminar(idOdontologo);
            session.setAttribute("mensajeExito", "Odontólogo eliminado correctamente.");
        } catch (NumberFormatException e) {
            session.setAttribute("mensajeError", "ID de odontólogo inválido");
        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", "El odontólogo no existe o ya fue eliminado");
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Error al eliminar odontólogo: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/odontologos");
    }
}
