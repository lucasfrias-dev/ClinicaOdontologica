package org.lucasf.consultorioodontologico.servlets.odontologos;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.Rol;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.services.OdontologoService;
import org.lucasf.consultorioodontologico.services.UsuarioService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/odontologos/registrar")
public class RegistrarOdontologoServlet extends HttpServlet {

    @Inject
    private OdontologoService odontologoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("title", req.getAttribute("title") + ": Registrar odontologo");

        // Redirigir a la vista JSP
        getServletContext().getRequestDispatcher("/pages/odontologos/altaOdontologo.jsp").forward(req, resp);
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
        String matricula = req.getParameter("matricula");
        String especialidad = req.getParameter("especialidad");
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
        Odontologo odontologo = new Odontologo(nombre, apellido, dni, telefono, direccion,
                fechaNacimiento, matricula, especialidad, usuario);

        // Guardar odontologo en la base de datos
        try {
            odontologoService.guardar(odontologo);
            resp.sendRedirect(req.getContextPath() + "/odontologos?success=Odontologo registrado con éxito");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
