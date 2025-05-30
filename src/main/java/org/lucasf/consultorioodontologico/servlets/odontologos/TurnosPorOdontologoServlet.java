package org.lucasf.consultorioodontologico.servlets.odontologos;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Turno;
import org.lucasf.consultorioodontologico.services.OdontologoService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Optional<Odontologo> odontologoOpt = odontologoService.porId(odontologoId);

            if (odontologoOpt.isPresent()) {
                Odontologo odontologo = odontologoOpt.get();

                List<Turno> todosLosTurnos = odontologo.getTurnos();
                LocalDateTime ahora = LocalDateTime.now();

                List<Turno> turnosFuturos = todosLosTurnos.stream()
                        .filter(t -> {
                            LocalDateTime fechaHora = LocalDateTime.of(t.getFechaTurno(), t.getHoraTurno());
                            return fechaHora.isAfter(ahora);
                        })
                        .sorted(Comparator.comparing(Turno::getFechaTurno).thenComparing(Turno::getHoraTurno))
                        .collect(Collectors.toList());

                List<Turno> turnosPasados = todosLosTurnos.stream()
                        .filter(t -> {
                            LocalDateTime fechaHora = LocalDateTime.of(t.getFechaTurno(), t.getHoraTurno());
                            return fechaHora.isBefore(ahora);
                        })
                        .sorted(Comparator.comparing(Turno::getFechaTurno).thenComparing(Turno::getHoraTurno).reversed())
                        .collect(Collectors.toList());

                req.setAttribute("odontologo", odontologo);
                req.setAttribute("turnosFuturos", turnosFuturos);
                req.setAttribute("turnosPasados", turnosPasados);

                req.getRequestDispatcher("/pages/odontologos/turnosOdontologo.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/odontologos?error=odontologo_no_encontrado");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/odontologos?error=id_invalido");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/odontologos?error=desconocido");
        }
    }
}
