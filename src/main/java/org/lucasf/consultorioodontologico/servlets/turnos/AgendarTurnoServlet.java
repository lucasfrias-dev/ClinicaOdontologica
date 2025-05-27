package org.lucasf.consultorioodontologico.servlets.turnos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Paciente;
import org.lucasf.consultorioodontologico.models.entities.Turno;
import org.lucasf.consultorioodontologico.services.OdontologoService;
import org.lucasf.consultorioodontologico.services.PacienteService;
import org.lucasf.consultorioodontologico.services.TurnoService;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@WebServlet("/turnos/agendar")
public class AgendarTurnoServlet extends HttpServlet {

    @Inject
    private TurnoService turnoService;

    @Inject
    private PacienteService pacienteService;

    @Inject
    private OdontologoService odontologoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            req.getSession().setAttribute("title", req.getAttribute("title") + ": Agendar Turno");

            req.setAttribute("pacientes", pacienteService.listar());
            req.setAttribute("odontologos", odontologoService.listar());

            if (req.getParameter("pacienteId") != null) {
                Long idPaciente = Long.parseLong(req.getParameter("pacienteId"));
                Paciente paciente = pacienteService.porId(idPaciente)
                        .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
                req.setAttribute("pacienteIdParam", paciente.getId());
            }

            req.getRequestDispatcher("/pages/turnos/altaTurno.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar datos para agendar turno");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Long idPaciente = Long.parseLong(req.getParameter("pacienteId"));
            Long idOdontologo = Long.parseLong(req.getParameter("odontologoId"));
            String afeccion = req.getParameter("afeccion");

            Paciente paciente = pacienteService.porId(idPaciente)
                    .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
            Odontologo odontologo = odontologoService.porId(idOdontologo)
                    .orElseThrow(() -> new EntityNotFoundException("Odontólogo no encontrado"));

            String fechaHoraStr = req.getParameter("fechaHora"); // viene en formato "dd/MM/yyyy HH:mm"

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);

            LocalDate fechaTurno = fechaHora.toLocalDate();
            LocalTime horaTurno = fechaHora.toLocalTime();


            Turno nuevoTurno = new Turno();
            nuevoTurno.setPaciente(paciente);
            nuevoTurno.setOdontologo(odontologo);
            nuevoTurno.setFechaTurno(fechaTurno);
            nuevoTurno.setHoraTurno(horaTurno);
            nuevoTurno.setAfeccion(afeccion);

            turnoService.guardar(nuevoTurno);
            req.setAttribute("mensajeExito", "Turno agendado correctamente.");
            resp.sendRedirect(req.getContextPath() + "/turnos");
        } catch (IllegalArgumentException e) {
            req.setAttribute("mensajeError", "Datos ingresados inválidos");
            resp.sendRedirect(req.getContextPath() + "/turnos/agendar");
        } catch (EntityNotFoundException e) {
            req.getSession().setAttribute("mensajeError", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/turnos/agendar");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al agendar turno");
        }
    }
}
