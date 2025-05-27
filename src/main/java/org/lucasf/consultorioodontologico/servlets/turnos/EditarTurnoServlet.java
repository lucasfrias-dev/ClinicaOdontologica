package org.lucasf.consultorioodontologico.servlets.turnos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.dtos.TurnoDisponibleDTO;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Turno;
import org.lucasf.consultorioodontologico.services.TurnoService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@WebServlet("/turnos/editar")
public class EditarTurnoServlet extends HttpServlet {

    @Inject
    private TurnoService turnoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Long idTurno = Long.valueOf(req.getParameter("idTurno"));
            Turno turno = turnoService.porId(idTurno)
                    .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));
            req.setAttribute("turno", turno);

            List<TurnoDisponibleDTO> turnosDisponibles =
                    turnoService.obtenerTurnosDisponibles(turno.getOdontologo().getId());
            req.setAttribute("turnosDisponibles", turnosDisponibles);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String fechaTurnoActual = turno.getFechaTurno().atTime(turno.getHoraTurno()).format(formatter);
            req.setAttribute("fechaTurnoActual", fechaTurnoActual);

            req.getRequestDispatcher("/pages/turnos/editarTurno.jsp").forward(req, resp);

        } catch (EntityNotFoundException e) {
            req.getSession().setAttribute("mensajeError", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/turnos");
        } catch (Exception e) {
            req.getSession().setAttribute("mensajeError", "No se pudo cargar el turno: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/turnos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        try {
            Long idTurno = Long.valueOf(req.getParameter("idTurno"));
            Turno turno = turnoService.porId(idTurno)
                    .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado"));


            // 1. ¿Desea editar el motivo?
            boolean editarAfeccion = req.getParameter("editarAfeccion") != null;
            if (editarAfeccion) {
                String nuevaAfeccion = req.getParameter("afeccion");
                if (nuevaAfeccion == null || nuevaAfeccion.trim().isEmpty()) {
                    throw new IllegalArgumentException("El motivo de consulta no puede estar vacío");
                }
                turno.setAfeccion(nuevaAfeccion.trim());
            }

            // 2. ¿Desea editar fecha/hora?
            boolean editarFechaHora = req.getParameter("editarFechaHora") != null;
            if (editarFechaHora) {
                String fechaHoraTexto = req.getParameter("fechaHora");
                if (fechaHoraTexto == null || fechaHoraTexto.isEmpty()) {
                    throw new IllegalArgumentException("Debe seleccionar una fecha y hora válida");
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime nuevaFechaHora = LocalDateTime.parse(fechaHoraTexto, formatter);
                turno.setFechaTurno(nuevaFechaHora.toLocalDate());
                turno.setHoraTurno(nuevaFechaHora.toLocalTime());
            }

            turnoService.guardar(turno);
            session.setAttribute("mensajeExito", "Turno editado correctamente.");

        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", e.getMessage());
        } catch (IllegalArgumentException e) {
            session.setAttribute("mensajeError", e.getMessage());
            // Redirige de vuelta a la página de edición con el ID
            resp.sendRedirect(req.getContextPath() + "/turnos/editar?idTurno=" + req.getParameter("idTurno"));
            return;
        } catch (Exception e) {
            session.setAttribute("mensajeError", "No se pudo editar el turno: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/turnos");
    }
}
