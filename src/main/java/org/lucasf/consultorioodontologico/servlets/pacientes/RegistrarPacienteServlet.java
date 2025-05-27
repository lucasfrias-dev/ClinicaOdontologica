package org.lucasf.consultorioodontologico.servlets.pacientes;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Paciente;
import org.lucasf.consultorioodontologico.models.entities.Responsable;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.services.PacienteService;
import org.lucasf.consultorioodontologico.services.ResponsableService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/pacientes/registrar")
public class RegistrarPacienteServlet extends HttpServlet {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private ResponsableService responsableService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("title", req.getAttribute("title") + ": Registrar paciente");

        // Redirigir a la vista JSP
        getServletContext().getRequestDispatcher("/pages/pacientes/altaPaciente.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // Obtener parámetros del formulario
            String nombre = req.getParameter("nombre");
            String apellido = req.getParameter("apellido");
            String dni = req.getParameter("dni");
            String telefono = req.getParameter("telefono");
            String direccion = req.getParameter("direccion");
            int edad = Integer.parseInt(req.getParameter("edad"));
            String tipoSangre = req.getParameter("tipoSangre");
            boolean obraSocial = req.getParameter("obraSocial") != null;
            boolean datosDesconocidos = req.getParameter("datosDesconocidosCheck") != null;

            // Validaciones básicas (excepto si marca datos desconocidos)
            if (!datosDesconocidos && (nombre == null || nombre.trim().isEmpty() ||
                    apellido == null || apellido.trim().isEmpty() || dni == null || dni.trim().isEmpty())) {
                throw new IllegalArgumentException("Datos básicos del paciente son requeridos");
            }

            // Crear el objeto Paciente
            Paciente paciente = new Paciente();
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setTelefono(telefono);
            paciente.setDireccion(direccion);
            paciente.setObraSocial(obraSocial);
            paciente.setTipoSangre(tipoSangre);

            // Manejar responsable si es menor de edad
            if (edad < 18) {
                String responsableNombre = req.getParameter("responsableNombre");
                String responsableApellido = req.getParameter("responsableApellido");
                String responsableDni = req.getParameter("responsableDni");
                String responsableTelefono = req.getParameter("responsableTelefono");
                String responsableDireccion = req.getParameter("responsableDireccion");
                String responsableParentesco = req.getParameter("responsableParentesco");

                if (!datosDesconocidos && (responsableNombre == null || responsableNombre.trim().isEmpty() ||
                        responsableDni == null || responsableDni.trim().isEmpty())) {
                    throw new IllegalArgumentException("Datos del responsable son obligatorios para menores de edad");
                }

                Responsable responsable = new Responsable();
                responsable.setNombre(responsableNombre);
                responsable.setApellido(responsableApellido);
                responsable.setDni(responsableDni);
                responsable.setTelefono(responsableTelefono);
                responsable.setDireccion(responsableDireccion);
                responsable.setTipoResponsabilidad(responsableParentesco);

                // Guardar responsable primero
                responsableService.guardar(responsable);
                paciente.setResponsable(responsable);
            }

            // Guardar el paciente
            pacienteService.guardar(paciente);

            // Redireccionar con mensaje de éxito
            req.setAttribute("mensajeExito", "Paciente creado correctamente.");
            resp.sendRedirect(req.getContextPath() + "/pacientes");;

        } catch (NumberFormatException e) {
            req.setAttribute("mensajeError", "Edad debe ser un número válido");
            req.getRequestDispatcher("/pages/pacientes/registrar.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("mensajeError", e.getMessage());
            req.getRequestDispatcher("/pages/pacientes/registrar.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("mensajeError", "Error al registrar paciente: " + e.getMessage());
            req.getRequestDispatcher("/pages/pacientes/registrar.jsp").forward(req, resp);
        }
    }
}
