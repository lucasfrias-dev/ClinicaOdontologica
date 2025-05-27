package org.lucasf.consultorioodontologico.servlets.horarios;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.services.HorarioService;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/horarios/editar")
public class EditarHorarioServlet extends HttpServlet {

    @Inject
    private HorarioService horarioService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Horario horario = horarioService.porId(Long.valueOf(req.getParameter("idHorario")))
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
            req.setAttribute("horario", horario);

            List<DiaSemana> dias = List.of(DiaSemana.values());
            req.setAttribute("diasSemana", dias);

            req.getRequestDispatcher("/pages/horarios/editarHorario.jsp").forward(req, resp);
        } catch (EntityNotFoundException e) {
            req.getSession().setAttribute("mensajeError", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/horarios");
        } catch (Exception e) {
            req.getSession().setAttribute("mensajeError", "No se pudo cargar el horario: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/horarios");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        try {
            Horario horario = horarioService.porId(Long.valueOf(req.getParameter("idHorario")))
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

            boolean editarDia = req.getParameter("editarDia") != null;
            if (editarDia) {
                String diaTexto = req.getParameter("dia");
                if (diaTexto == null || diaTexto.isEmpty()) {
                    throw new IllegalArgumentException("Debe seleccionar un día");
                }

                DiaSemana dia = DiaSemana.valueOf(diaTexto);
                horario.setDia(dia);
            }

            boolean editarHoraInicio = req.getParameter("editarInicio") != null;
            if (editarHoraInicio) {
                String horaInicioTexto = req.getParameter("horaInicio");
                if (horaInicioTexto == null || horaInicioTexto.isEmpty()) {
                    throw new IllegalArgumentException("Debe seleccionar una hora de inicio");
                }

                LocalTime horaInicio = LocalTime.parse(horaInicioTexto);
                horario.setHoraInicio(horaInicio);
            }

            boolean editarHoraFin = req.getParameter("editarFin") != null;
            if (editarHoraFin) {
                String horaFinTexto = req.getParameter("horaFin");
                if (horaFinTexto == null || horaFinTexto.isEmpty()) {
                    throw new IllegalArgumentException("Debe seleccionar una hora de fin");
                }

                LocalTime horaFin = LocalTime.parse(horaFinTexto);
                horario.setHoraFin(horaFin);
            }

            horarioService.guardar(horario);
            session.setAttribute("mensajeExito", "Horario editado correctamente.");
        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", e.getMessage());
        } catch (IllegalArgumentException e) {
            session.setAttribute("mensajeError", e.getMessage());
            // Redirige de vuelta a la página de edición con el ID
            resp.sendRedirect(req.getContextPath() + "/horarios/editar?idHorario=" + req.getParameter("idHorario"));
            return;
        } catch (Exception e) {
            session.setAttribute("mensajeError", "No se pudo editar el horario: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/horarios");
    }
}
