package org.lucasf.consultorioodontologico.servlets.horarios;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.services.HorarioService;
import org.lucasf.consultorioodontologico.services.OdontologoService;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/horarios/asignar")
public class AsignarHorarioServlet extends HttpServlet {

    @Inject
    private HorarioService horarioService;

    @Inject
    private OdontologoService odontologoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("title", req.getAttribute("title") + ": Asignar Horario");

        try {
            if (req.getParameter("idPaciente") != null) {
                Long idPaciente = Long.parseLong(req.getParameter("idPaciente"));
                req.setAttribute("idPaciente", idPaciente);
            }

            List<Horario> horarios = horarioService.listar();
            List<Odontologo> odontologos = odontologoService.listar();

            req.setAttribute("horarios", horarios);
            req.setAttribute("odontologos", odontologos);

            req.setAttribute("noHorarios", horarios.isEmpty());
            req.setAttribute("noOdontologos", odontologos.isEmpty());

            getServletContext().getRequestDispatcher("/pages/horarios/asignarHorario.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Obtener parámetros
        String odontologoIdStr = req.getParameter("odontologoId");
        String horarioIdStr = req.getParameter("horarioId");
        String diaStr = req.getParameter("dia");
        String horaInicioStr = req.getParameter("horaInicio");
        String horaFinStr = req.getParameter("horaFin");
        boolean crearNuevo = req.getParameter("crearNuevo") != null;

        // 2. Validar odontólogo (requerido siempre)
        if (odontologoIdStr == null || odontologoIdStr.isEmpty()) {
            enviarError(req, resp, "Seleccione un odontólogo");
            return;
        }

        // 3. Validar según el modo (crear nuevo o usar existente)
        if (crearNuevo) {
            // Validación para nuevo horario
            if (diaStr == null || diaStr.isEmpty()) {
                enviarError(req, resp, "Seleccione un día para el nuevo horario");
                return;
            }
            if (horaInicioStr == null || horaInicioStr.isEmpty()) {
                enviarError(req, resp, "Ingrese la hora de inicio");
                return;
            }
            if (horaFinStr == null || horaFinStr.isEmpty()) {
                enviarError(req, resp, "Ingrese la hora de fin");
                return;
            }
        } else {
            // Validación para horario existente
            if (horarioIdStr == null || horarioIdStr.isEmpty()) {
                enviarError(req, resp, "Seleccione un horario existente o elija la opción de crear uno nuevo");
                return;
            }
        }

        try {
            // 4. Obtener odontólogo
            Long odontologoId = Long.parseLong(odontologoIdStr);
            Odontologo odontologo = odontologoService.porId(odontologoId)
                    .orElseThrow(() -> new IllegalArgumentException("Odontólogo no encontrado"));

            Horario horario;

            if (crearNuevo) {
                // Crear nuevo horario
                DiaSemana dia = DiaSemana.valueOf(diaStr);
                LocalTime horaInicio = LocalTime.parse(horaInicioStr);
                LocalTime horaFin = LocalTime.parse(horaFinStr);

                if (horaInicio.isAfter(horaFin)) {
                    enviarError(req, resp, "La hora de inicio debe ser anterior a la hora de fin");
                    return;
                }

                horario = new Horario();
                horario.setDia(dia);
                horario.setHoraInicio(horaInicio);
                horario.setHoraFin(horaFin);
            } else {
                // Usar horario existente
                Long horarioId = Long.parseLong(horarioIdStr);
                horario = horarioService.porId(horarioId)
                        .orElseThrow(() -> new IllegalArgumentException("Horario no encontrado"));
            }

            // 5. Asignar horario
            horarioService.asignarHorario(odontologo, horario);

            // 6. Redirigir con mensaje de éxito
            resp.sendRedirect(req.getContextPath() + "/horarios/asignar?exito=1");

        } catch (IllegalArgumentException e) {
            enviarError(req, resp, e.getMessage());
        } catch (DateTimeParseException e) {
            enviarError(req, resp, "Formato de hora inválido");
        } catch (Exception e) {
            enviarError(req, resp, "Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Método auxiliar para manejar errores
    private void enviarError(HttpServletRequest req, HttpServletResponse resp, String mensaje)
            throws ServletException, IOException {
        req.setAttribute("error", mensaje);
        req.getRequestDispatcher("/pages/horarios/asignarHorario.jsp").forward(req, resp);
    }
}
