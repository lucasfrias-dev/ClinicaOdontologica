package org.lucasf.consultorioodontologico.servlets.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalida la sesión
        HttpSession session = request.getSession(false); // false para no crear si no existe
        if (session != null) {
            session.invalidate();
        }

        // Redirecciona al login
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
