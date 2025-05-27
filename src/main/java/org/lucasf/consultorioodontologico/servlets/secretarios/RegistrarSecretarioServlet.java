package org.lucasf.consultorioodontologico.servlets.secretarios;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.Rol;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Secretario;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.services.SecretarioService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/secretarios/registrar")
public class RegistrarSecretarioServlet extends HttpServlet {

    @Inject
    private SecretarioService secretarioService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("title", req.getAttribute("title") + ": Registrar secretario");

        // Redirigir a la vista JSP
        getServletContext().getRequestDispatcher("/pages/secretarios/altaSecretario.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("title", req.getAttribute("title") + ": Registrar odontologo");

        // Obtener datos del formulario
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String dni = req.getParameter("dni");
        String telefono = req.getParameter("telefono");
        String direccion = req.getParameter("direccion");
        String sector = req.getParameter("sector");
        // Validar fecha de nacimiento
        LocalDate fechaNacimiento;
        try {
            fechaNacimiento = LocalDate.parse(req.getParameter("fechaNacimiento"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha de nacimiento inválida.");
        }

        String contrasenia = req.getParameter("contrasenia");
        String rol = req.getParameter("rol");

        // Generar nombre de usuario automáticamente
        String nombreUsuario = (nombre + apellido).toLowerCase().replace(" ", "");

        // Crear entidad Usuario
        Usuario usuario = new Usuario(nombreUsuario, contrasenia, Rol.valueOf(rol));

        // Crear entidad Odontologo y setear campos
        Secretario secretario = new Secretario(
                nombre,
                apellido,
                dni,
                telefono,
                direccion,
                fechaNacimiento,
                sector,
                usuario
        );

        // Guardar odontologo en la base de datos
        try {
            secretarioService.guardar(secretario);
            resp.sendRedirect(req.getContextPath() + "/secretarios?success=Odontologo registrado con éxito");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
