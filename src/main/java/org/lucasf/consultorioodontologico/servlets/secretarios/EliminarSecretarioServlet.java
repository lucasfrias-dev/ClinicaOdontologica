package org.lucasf.consultorioodontologico.servlets.secretarios;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.services.SecretarioService;

import java.io.IOException;

@WebServlet("/secretarios/eliminar")
public class EliminarSecretarioServlet extends HttpServlet {

    @Inject
    private SecretarioService secretarioService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        try {
            Long idSecretario = Long.valueOf(req.getParameter("idSecretario"));
            secretarioService.eliminar(idSecretario);
            session.setAttribute("mensajeExito", "Secretario eliminado correctamente.");
        } catch (NumberFormatException e) {
            session.setAttribute("mensajeError", "ID de secretario inválido");
        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", "El secretario no existe o ya fue eliminado");
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Error al eliminar secretario: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/secretarios");
    }
}
