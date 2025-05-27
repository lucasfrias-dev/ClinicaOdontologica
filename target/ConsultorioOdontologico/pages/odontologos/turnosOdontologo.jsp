<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Turno" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Odontologo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%@ include file="../../components/header.jsp" %>

<body id="page-top">

<div id="wrapper">
    <%@ include file="../../components/sidebar.jsp" %>

    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <%@ include file="../../components/navbar.jsp" %>

            <div class="container-fluid">

                <h1 class="h3 mb-2 text-gray-800">Turnos del Odontólogo</h1>

                <%
                    Odontologo odontologo = (Odontologo) request.getAttribute("odontologo");
                    List<Turno> turnos = (List<Turno>) request.getAttribute("turnos");
                    DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
                %>

                <p class="mb-4">
                    Listado de turnos asignados al odontólogo
                    <strong><%= odontologo.getNombre() %> <%= odontologo.getApellido() %></strong>.
                </p>

                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Turnos asignados</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Afección</th>
                                    <th>Paciente</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Afección</th>
                                    <th>Paciente</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <%
                                    if (turnos != null && !turnos.isEmpty()) {
                                        for (Turno turno : turnos) {
                                %>
                                <tr>
                                    <td><%= turno.getFechaTurno() != null ? turno.getFechaTurno().format(fechaFormatter) : "" %></td>
                                    <td><%= turno.getHoraTurno() != null ? turno.getHoraTurno().format(horaFormatter) : "" %></td>
                                    <td><%= turno.getAfeccion() != null ? turno.getAfeccion() : "No especificada" %></td>
                                    <td><%= turno.getPaciente() != null ? turno.getPaciente().getNombre() + " " + turno.getPaciente().getApellido() : "No asignado" %></td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="4" class="text-center">No hay turnos asignados.</td>
                                </tr>
                                <% } %>
                                </tbody>
                            </table>
                        </div>
                        <a href="${pageContext.request.contextPath}/odontologos" class="btn btn-secondary mt-3">
                            Volver al listado de odontólogos
                        </a>
                    </div>
                </div>

            </div>
        </div>

        <%@ include file="../../components/footer.jsp" %>
    </div>
</div>

<%@ include file="../../components/scripts.jsp" %>

<script src="../../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<script>
    $(document).ready(function () {
        $('#dataTable').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.20/i18n/Spanish.json"
            }
        });
    });
</script>

</body>
</html>
