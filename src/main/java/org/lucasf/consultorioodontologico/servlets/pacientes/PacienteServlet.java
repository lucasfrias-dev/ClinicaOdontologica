package org.lucasf.consultorioodontologico.servlets.pacientes;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Paciente;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.services.PacienteService;

import java.io.IOException;
import java.util.List;

@WebServlet("/pacientes")
public class PacienteServlet extends HttpServlet {

    @Inject
    private PacienteService pacienteService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Manejo seguro del título
        String title = (String) req.getAttribute("title");
        req.setAttribute("title", (title != null ? title : "") + ": Listado de pacientes");

        // Recuperar y limpiar mensajes de la sesión
        HttpSession session = req.getSession();
        req.setAttribute("mensajeExito", session.getAttribute("mensajeExito"));
        req.setAttribute("mensajeError", session.getAttribute("mensajeError"));
        session.removeAttribute("mensajeExito");
        session.removeAttribute("mensajeError");

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

            List<Paciente> pacientes;
            if (usuario != null && usuario.getRol().equals("ODONTOLOGO")) {
                // Suponiendo que odontólogo está relacionado con usuario
                pacientes = pacienteService.buscarPorOdontologo(usuario.getIdUsuario());
            } else {
                pacientes = pacienteService.listar(); // Admin o secretario
            }

            req.setAttribute("pacientes", pacientes);
            getServletContext().getRequestDispatcher("/pages/pacientes/listarPacientes.jsp").forward(req, resp);

        } catch (Exception e) {
            // Log del error (idealmente con algún logger)
            e.printStackTrace();
            // Establecer mensaje de error
            req.setAttribute("mensajeError", "Error al cargar los pacientes");
            req.getRequestDispatcher("/pages/turnos/listarPacientes.jsp").forward(req, resp);
        }
    }
}
