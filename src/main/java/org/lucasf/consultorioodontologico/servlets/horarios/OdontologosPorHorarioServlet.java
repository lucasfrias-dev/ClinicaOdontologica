package org.lucasf.consultorioodontologico.servlets.horarios;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.services.HorarioService;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@WebServlet("/horarios/odontologos")
public class OdontologosPorHorarioServlet extends HttpServlet {

    @Inject
    private HorarioService horarioService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("idHorario");

        // Log para depuración
        System.out.println("ID recibido: " + idParam);

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro idHorario faltante");
            return;
        }

        try {
            Long horarioId = Long.parseLong(idParam);
            Optional<Horario> horario = horarioService.porId(horarioId);

            if (horario.isPresent()) {
                req.setAttribute("horario", horario.get()); // Cambiado de Optional a objeto directo
                req.setAttribute("odontologos", horario.get().getOdontologos());
                req.getRequestDispatcher("/pages/horarios/odontologosHorario.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/horarios?error=horario_no_encontrado");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/horarios?error=id_invalido");
        } catch (Exception e) {
            e.printStackTrace(); // Log para ver errores inesperados
            resp.sendRedirect(req.getContextPath() + "/horarios?error=desconocido");
        }
    }
}
