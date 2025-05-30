package org.lucasf.consultorioodontologico.servlets.turnos;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.Rol;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Turno;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.services.TurnoService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/turnos")
public class TurnoServlet extends HttpServlet {

    @Inject
    private TurnoService turnoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Manejo seguro del título
        String title = (String) req.getAttribute("title");
        req.setAttribute("title", (title != null ? title : "") + ": Listado de turnos");

        // Recuperar y limpiar mensajes de la sesión
        HttpSession session = req.getSession();
        req.setAttribute("mensajeExito", session.getAttribute("mensajeExito"));
        req.setAttribute("mensajeError", session.getAttribute("mensajeError"));
        session.removeAttribute("mensajeExito");
        session.removeAttribute("mensajeError");

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            List<Turno> todosLosTurnos ;

            if (usuario != null && usuario.getRol() == Rol.ODONTOLOGO) {
                // Turnos solo del odontólogo logueado
                todosLosTurnos = turnoService.buscarPorOdontologo(usuario.getIdUsuario());
            } else {
                // Todos los turnos
                todosLosTurnos = turnoService.listar();
            }

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

            req.setAttribute("turnosFuturos", turnosFuturos);
            req.setAttribute("turnosPasados", turnosPasados);
            req.getRequestDispatcher("/pages/turnos/listarTurnos.jsp").forward(req, resp);

        } catch (Exception e) {
            // Log del error (idealmente con algún logger)
            e.printStackTrace();
            // Establecer mensaje de error
            req.setAttribute("mensajeError", "Error al cargar los turnos");
            req.getRequestDispatcher("/pages/turnos/listarTurnos.jsp").forward(req, resp);
        }
    }
}
