package org.lucasf.consultorioodontologico.servlets.odontologos;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.dtos.TurnoDisponibleDTO;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.services.OdontologoService;
import org.lucasf.consultorioodontologico.services.TurnoService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/odontologos/turnosDisponibles")
public class TurnosDisponiblesServlet extends HttpServlet {

    @Inject
    private TurnoService turnoService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Long idOdontologo = Long.parseLong(req.getParameter("odontologoId"));

            List<TurnoDisponibleDTO> turnosDisponibles =
                    turnoService.obtenerTurnosDisponibles(idOdontologo);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), turnosDisponibles);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de odontólogo inválido");
        } catch (EntityNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener horarios");
        }
    }
}


