package org.lucasf.consultorioodontologico.servlets.secretarios;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.entities.Secretario;
import org.lucasf.consultorioodontologico.services.SecretarioService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/secretarios/editar")
public class EditarSecretarioServlet extends HttpServlet {

    @Inject
    private SecretarioService secretarioService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Long idSecretario = Long.valueOf(req.getParameter("idSecretario"));
            Secretario secretario = secretarioService.porId(idSecretario)
                    .orElseThrow(() -> new EntityNotFoundException("Secretario no encontrado"));

            req.setAttribute("secretario", secretario);

            req.getRequestDispatcher("/pages/secretarios/editarSecretario.jsp").forward(req, resp);
        } catch (EntityNotFoundException e) {
            req.getSession().setAttribute("mensajeError", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/secretarios");
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("mensajeError", "No se pudo cargar el secretario: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/secretarios");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        try {
            Long idSecretario = Long.valueOf(req.getParameter("idSecretario"));
            Secretario secretario = secretarioService.porId(idSecretario)
                    .orElseThrow(() -> new EntityNotFoundException("Secretario no encontrado"));

            // Editar nombre y apellido
            if (req.getParameter("nombre") != null && req.getParameter("apellido") != null && req.getParameter("editarNombreCheck") != null) {
                String nombre = req.getParameter("nombre").trim();
                String apellido = req.getParameter("apellido").trim();
                if (!nombre.isEmpty()) secretario.setNombre(nombre);
                if (!apellido.isEmpty()) secretario.setApellido(apellido);
            }

            // Editar DNI y teléfono
            if (req.getParameter("dni") != null && req.getParameter("telefono") != null && req.getParameter("editarContactoCheck") != null) {
                String dni = req.getParameter("dni").trim();
                String telefono = req.getParameter("telefono").trim();
                if (!dni.isEmpty()) secretario.setDni(dni);
                secretario.setTelefono(telefono); // puede estar vacío
            }

            // Editar dirección
            if (req.getParameter("direccion") != null && req.getParameter("editarDireccionCheck") != null) {
                String direccion = req.getParameter("direccion").trim();
                secretario.setDireccion(direccion); // puede estar vacío
            }

            // Editar fecha de nacimiento
            if (req.getParameter("fechaNacimiento") != null && req.getParameter("editarFechaNacimientoCheck") != null) {
                String fechaNacimientoStr = req.getParameter("fechaNacimiento").trim();
                if (!fechaNacimientoStr.isEmpty()) {
                    try {
                        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                        secretario.setFechaNacimiento(fechaNacimiento);
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Fecha de nacimiento inválida. Use el formato AAAA-MM-DD.");
                    }
                }
            }

            // Editar sector
            if (req.getParameter("sector") != null && req.getParameter("editarSectorCheck") != null) {
                String sector = req.getParameter("sector").trim();
                if (!sector.isEmpty()) {
                    secretario.setSector(sector);
                }
            }

            secretarioService.guardar(secretario);
            session.setAttribute("mensajeExito", "Secretario actualizado correctamente.");

        } catch (EntityNotFoundException e) {
            session.setAttribute("mensajeError", e.getMessage());
        } catch (IllegalArgumentException e) {
            session.setAttribute("mensajeError", e.getMessage());
            // Redirige de vuelta a la página de edición con el ID
            resp.sendRedirect(req.getContextPath() + "/secretarios/editar?idSecretario=" + req.getParameter("idSecretario"));
            return;
        } catch (Exception e) {
            session.setAttribute("mensajeError", "No se pudo editar el secretario: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/secretarios");
    }

}
