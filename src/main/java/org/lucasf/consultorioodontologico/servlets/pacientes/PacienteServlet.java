package org.lucasf.consultorioodontologico.servlets.pacientes;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.Rol;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Paciente;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.services.OdontologoService;
import org.lucasf.consultorioodontologico.services.PacienteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/pacientes")
public class PacienteServlet extends HttpServlet {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private OdontologoService odontologoService;

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

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        try {
            List<Paciente> pacientes;
            if (usuario != null && usuario.getRol() == Rol.ODONTOLOGO) {
                Optional<Odontologo> optionalOdontologo = odontologoService.buscarPorUsuarioId(usuario.getIdUsuario());

                if (optionalOdontologo.isPresent()) {
                    Odontologo odontologo = optionalOdontologo.get();
                    System.out.println("Odontólogo encontrado: " + odontologo.getNombre() + " " + odontologo.getApellido());
                    pacientes = pacienteService.buscarPorOdontologo(odontologo.getId());
                    System.out.println("Pacientes asociados al odontólogo: " + pacientes.size());
                } else {
                    pacientes = new ArrayList<>(); // o podrías lanzar una excepción o redirigir
                    req.setAttribute("mensajeError", "No se encontró el odontólogo asociado al usuario.");
                }
            } else {
                pacientes = pacienteService.listar(); // Admin o secretario
                System.out.println("Listado de pacientes para admin o secretario: " + pacientes.size());
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
