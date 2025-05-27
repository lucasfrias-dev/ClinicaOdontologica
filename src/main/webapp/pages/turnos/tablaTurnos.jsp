<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Turno" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Usuario" %>
<%@ page import="org.lucasf.consultorioodontologico.models.Rol" %>
<%@ page import="java.util.List" %>
<%
    String nombreLista = request.getParameter("nombreLista");
    List<Turno> turnos = (List<Turno>) request.getAttribute(nombreLista);
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    boolean esOdontologo = usuario != null && usuario.getRol() == Rol.ODONTOLOGO;
%>

<div class="card shadow mb-4">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered dataTableTurnos" width="100%" cellspacing="0">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Fecha</th>
                    <th>Hora</th>
                    <th>Afección</th>
                    <% if (!esOdontologo) { %><th>Odontólogo</th><% } %>
                    <th>Paciente</th>
                    <% if (!esOdontologo) { %><th>Acciones</th><% } %>
                </tr>
                </thead>
                <tbody>
                <% if (turnos != null && !turnos.isEmpty()) {
                    for (Turno t : turnos) {
                %>
                <tr>
                    <td><%= t.getIdTurno() %></td>
                    <td><%= t.getFechaTurno() %></td>
                    <td><%= t.getHoraTurno() %></td>
                    <td><%= t.getAfeccion() %></td>
                    <% if (!esOdontologo) { %>
                    <td><%= t.getOdontologo().getNombre() %> <%= t.getOdontologo().getApellido() %></td>
                    <% } %>
                    <td><%= t.getPaciente().getNombre() %> <%= t.getPaciente().getApellido() %></td>
                    <% if (!esOdontologo) { %>
                    <td>
                        <div class="btn-group" role="group">
                            <a href="${pageContext.request.contextPath}/turnos/editar?idTurno=<%= t.getIdTurno() %>" class="btn btn-sm btn-primary">
                                <i class="fas fa-edit"></i> Editar
                            </a>
                            <form method="post" action="${pageContext.request.contextPath}/turnos/eliminar" style="display:inline;">
                                <input type="hidden" name="idTurno" value="<%= t.getIdTurno() %>"/>
                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('¿Está seguro que desea eliminar este turno?');">
                                    <i class="fas fa-trash"></i> Eliminar
                                </button>
                            </form>
                        </div>
                    </td>
                    <% } %>
                </tr>
                <% }
                } else { %>
                <tr>
                    <td colspan="<%= esOdontologo ? 5 : 7 %>" class="text-center">No hay turnos en esta sección.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
