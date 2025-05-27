package org.lucasf.consultorioodontologico.servlets.odontologos;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.services.OdontologoService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/odontologos/turnos")
public class TurnosPorOdontologoServlet extends HttpServlet {

    @Inject
    private OdontologoService odontologoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idParam = req.getParameter("idOdontologo");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro idOdontologo faltante");
            return;
        }

        try {
            Long odontologoId = Long.parseLong(idParam);
            Optional<Odontologo> odontologo = odontologoService.porId(odontologoId);

            if (odontologo.isPresent()) {
                req.setAttribute("odontologo", odontologo.get()); // Cambiado de Optional a objeto directo
                req.setAttribute("turnos", odontologo.get().getTurnos());
                req.getRequestDispatcher("/pages/odontologos/turnosOdontologo.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/odontologos?error=odontologo_no_encontrado");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/odontologos?error=id_invalido");
        } catch (Exception e) {
            e.printStackTrace(); // Log para ver errores inesperados
            resp.sendRedirect(req.getContextPath() + "/odontologos?error=desconocido");
        }
    }
}
