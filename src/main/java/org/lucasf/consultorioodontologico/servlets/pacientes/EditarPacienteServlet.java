package org.lucasf.consultorioodontologico.servlets.pacientes;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.entities.Paciente;
import org.lucasf.consultorioodontologico.models.entities.Responsable;
import org.lucasf.consultorioodontologico.services.PacienteService;
import org.lucasf.consultorioodontologico.services.ResponsableService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/pacientes/editar")
public class EditarPacienteServlet extends HttpServlet {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private ResponsableService responsableService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("idPaciente"));
            Paciente paciente = pacienteService.porId(id).orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
            List<Responsable> responsables = responsableService.listar();

            req.setAttribute("paciente", paciente);
            req.setAttribute("responsables", responsables);
            req.getRequestDispatcher("/pages/pacientes/editarPaciente.jsp").forward(req, resp);

        } catch (EntityNotFoundException e) {
            req.getSession().setAttribute("mensajeError", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/pacientes");
        } catch (Exception e) {
            req.getSession().setAttribute("mensajeError", "No se pudo cargar el paciente: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/pacientes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        try {
            Long id = Long.parseLong(req.getParameter("idPaciente"));
            Paciente paciente = pacienteService.porId(id).orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

            if (req.getParameter("editarNombre") != null)
                paciente.setNombre(req.getParameter("nombre"));

            if (req.getParameter("editarApellido") != null)
                paciente.setApellido(req.getParameter("apellido"));

            if (req.getParameter("editarDni") != null)
                paciente.setDni(req.getParameter("dni"));

            if (req.getParameter("editarTelefono") != null)
                paciente.setTelefono(req.getParameter("telefono"));

            if (req.getParameter("editarDireccion") != null)
                paciente.setDireccion(req.getParameter("direccion"));

            if (req.getParameter("editarFechaNacimiento") != null) {
                String fechaNacimientoStr = req.getParameter("fechaNacimiento").trim();
                if (!fechaNacimientoStr.isEmpty()) {
                    try {
                        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr); // yyyy-MM-dd
                        paciente.setFechaNacimiento(fechaNacimiento);
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Formato de fecha inválido (debe ser yyyy-MM-dd)");
                    }
                }
            }

            if (req.getParameter("editarTipoSangre") != null)
                paciente.setTipoSangre(req.getParameter("tipoSangre"));

            if (req.getParameter("editarObraSocial") != null)
                paciente.setObraSocial(Boolean.parseBoolean(req.getParameter("obraSocial")));

            if (req.getParameter("editarResponsable") != null) {
                Long idResponsable = Long.parseLong(req.getParameter("responsableId"));
                Responsable responsable = responsableService.porId(idResponsable)
                        .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado"));
                paciente.setResponsable(responsable);
            }

            pacienteService.guardar(paciente);
            session.setAttribute("mensajeExito", "Paciente editado correctamente");

        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", e.getMessage());
        } catch (IllegalArgumentException e) {
            session.setAttribute("mensajeError", e.getMessage());
            // Redirige de vuelta a la página de edición con el ID
            resp.sendRedirect(req.getContextPath() + "/pacientes/editar?idPaciente=" + req.getParameter("idPaciente"));
            return;
        } catch (Exception e) {
            session.setAttribute("mensajeError", "No se pudo editar el paciente: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/pacientes");
    }
}
