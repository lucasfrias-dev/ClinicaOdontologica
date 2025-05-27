package org.lucasf.consultorioodontologico.servlets.odontologos;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.services.OdontologoService;

import java.io.IOException;
import java.util.List;

@WebServlet("/odontologos")
public class OdontologoServlet extends HttpServlet {

    @Inject
    private OdontologoService odontologoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("title", req.getAttribute("title") + ": Listado de odontologos");

        try {
            List<Odontologo> odontologos = odontologoService.listar();
            req.setAttribute("odontologos", odontologos);
            getServletContext().getRequestDispatcher("/pages/odontologos/listarOdontologos.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
