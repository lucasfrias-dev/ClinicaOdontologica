package org.lucasf.consultorioodontologico.servlets.odontologos;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.services.OdontologoService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/odontologos/editar")
public class EditarOdontologoServlet extends HttpServlet {

    @Inject
    private OdontologoService odontologoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Long idOdontologo = Long.valueOf(req.getParameter("idOdontologo"));
            Odontologo odontologo = odontologoService.porId(idOdontologo)
                    .orElseThrow(() -> new EntityNotFoundException("Odontólogo no encontrado"));

            req.setAttribute("odontologo", odontologo);

            req.getRequestDispatcher("/pages/odontologos/editarOdontologo.jsp").forward(req, resp);
        } catch (EntityNotFoundException e) {
            req.getSession().setAttribute("mensajeError", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/odontologos");
        } catch (Exception e) {
            req.getSession().setAttribute("mensajeError", "No se pudo cargar el odontólogo: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/odontologos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        try {
            Long idOdontologo = Long.valueOf(req.getParameter("idOdontologo"));
            Odontologo odontologo = odontologoService.porId(idOdontologo)
                    .orElseThrow(() -> new EntityNotFoundException("Odontólogo no encontrado"));

            // Editar nombre y apellido
            if (req.getParameter("nombre") != null && req.getParameter("apellido") != null && req.getParameter("editarNombreCheck") != null) {
                String nombre = req.getParameter("nombre").trim();
                String apellido = req.getParameter("apellido").trim();
                if (!nombre.isEmpty()) odontologo.setNombre(nombre);
                if (!apellido.isEmpty()) odontologo.setApellido(apellido);
            }

            // Editar DNI y teléfono
            if (req.getParameter("dni") != null && req.getParameter("telefono") != null && req.getParameter("editarContactoCheck") != null) {
                String dni = req.getParameter("dni").trim();
                String telefono = req.getParameter("telefono").trim();
                if (!dni.isEmpty()) odontologo.setDni(dni);
                odontologo.setTelefono(telefono); // puede estar vacío
            }

            // Editar dirección
            if (req.getParameter("direccion") != null && req.getParameter("editarDireccionCheck") != null) {
                String direccion = req.getParameter("direccion").trim();
                odontologo.setDireccion(direccion); // puede estar vacío
            }

            // Editar fecha de nacimiento
            if (req.getParameter("fechaNacimiento") != null && req.getParameter("editarNacimientoCheck") != null) {
                String fechaNacimientoStr = req.getParameter("fechaNacimiento").trim();
                if (!fechaNacimientoStr.isEmpty()) {
                    try {
                        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr); // Formato ISO-8601 (yyyy-MM-dd)
                        odontologo.setFechaNacimiento(fechaNacimiento);
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Formato de fecha inválido (debe ser yyyy-MM-dd)");
                    }
                }
            }


            // Editar matrícula y especialidad
            if (req.getParameter("matricula") != null && req.getParameter("especialidad") != null && req.getParameter("editarProfesionalCheck") != null) {
                String matricula = req.getParameter("matricula").trim();
                String especialidad = req.getParameter("especialidad").trim();
                if (!matricula.isEmpty()) odontologo.setMatricula(matricula);
                odontologo.setEspecialidad(especialidad); // puede estar vacía
            }

            odontologoService.guardar(odontologo);
            session.setAttribute("mensajeExito", "Odontólogo actualizado correctamente.");

        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", e.getMessage());
        } catch (IllegalArgumentException e) {
            session.setAttribute("mensajeError", e.getMessage());
            // Redirige de vuelta a la página de edición con el ID
            resp.sendRedirect(req.getContextPath() + "/odontologos/editar?idOdontologo=" + req.getParameter("idOdontologo"));
            return;
        } catch (Exception e) {
            session.setAttribute("mensajeError", "No se pudo editar el odontólogo: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/odontologos");
    }
}
