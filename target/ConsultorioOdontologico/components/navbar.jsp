<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Obtener el usuario logueado de la sesión
    Object usuarioObj = session.getAttribute("usuario");
    String nombreUsuario = "Invitado";
    String rol = "Sin rol";

    if (usuarioObj != null && usuarioObj instanceof org.lucasf.consultorioodontologico.models.entities.Usuario) {
        org.lucasf.consultorioodontologico.models.entities.Usuario usuario =
                (org.lucasf.consultorioodontologico.models.entities.Usuario) usuarioObj;

        nombreUsuario = usuario.getNombreUsuario();

        switch (usuario.getRol()) {
            case ADMIN:
                rol = "Administrador";
                break;
            case SECRETARIO:
                rol = "Secretario";
                break;
            case ODONTOLOGO:
                rol = "Odontólogo";
                break;
            default:
                rol = "Usuario";
        }
    }
%>

<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
        <i class="fa fa-bars"></i>
    </button>

    <ul class="navbar-nav ml-auto">
        <div class="topbar-divider d-none d-sm-block"></div>
        <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%= nombreUsuario %> | <%= rol %></span>
                <img class="img-profile rounded-circle" src="${pageContext.request.contextPath}/img/undraw_profile.svg">
            </a>
            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                 aria-labelledby="userDropdown">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/pages/perfil.jsp">
                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                    Perfil
                </a>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/pages/configuracion.jsp">
                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                    Configuración
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                    Cerrar sesión
                </a>
            </div>
        </li>
    </ul>
</nav>
