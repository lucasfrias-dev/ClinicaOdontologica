package org.lucasf.consultorioodontologico.servlets.login;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.services.UsuarioService;
import org.lucasf.consultorioodontologico.utils.PasswordEncoder;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            if (username == null || username.isBlank() || password == null || password.isBlank()) {
                req.setAttribute("errorLogin", "El nombre de usuario y la contraseña son obligatorios.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }

            Usuario usuario = usuarioService.buscarPorNombreUsuario(username);
            System.out.println("Usuario encontrado: " + (usuario != null ? usuario.getNombreUsuario() : "null"));

            if (usuario != null) {
                boolean passOk = passwordEncoder.verify(password, usuario.getContrasenia());
                System.out.println("Password correcto: " + passOk);
                if (passOk) {
                    HttpSession session = req.getSession();
                    session.setAttribute("usuario", usuario);
                    System.out.println("Usuario guardado en sesión: " + usuario.getNombreUsuario());
                    resp.sendRedirect(req.getContextPath() + "/index.jsp");
                    return;
                }
            }

            req.setAttribute("errorLogin", "Usuario o contraseña incorrectos.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorLogin", "Ha ocurrido un error inesperado. Intente nuevamente.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

}
