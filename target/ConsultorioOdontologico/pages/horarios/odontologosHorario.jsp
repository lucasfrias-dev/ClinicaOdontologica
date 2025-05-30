<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Odontologo" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Horario" %>
<%@ page import="java.util.Set" %>
<%@ include file="../../components/header.jsp" %>

<body id="page-top">

<div id="wrapper">
    <%@ include file="../../components/sidebar.jsp" %>

    <div id="content-wrapper" class="d-flex flex-column">

        <div id="content">
            <%@ include file="../../components/navbar.jsp" %>

            <div class="container-fluid">

                <%
                    Horario horario = (Horario) request.getAttribute("horario");
                    Set<Odontologo> odontologos = (Set<Odontologo>) request.getAttribute("odontologos");
                %>

                <h1 class="h3 mb-2 text-gray-800">Odontólogos con Horario:
                    <%= horario.getDia() %>
                    de <%= horario.getHoraInicio() != null ? horario.getHoraInicio().toString() : "" %>
                    a <%= horario.getHoraFin() != null ? horario.getHoraFin().toString() : "" %>
                </h1>

                <p class="mb-4">Listado de odontólogos asignados a este horario.</p>

                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Odontólogos</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Matrícula</th>
                                    <th>Especialidad</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Matrícula</th>
                                    <th>Especialidad</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <%
                                    if (odontologos != null && !odontologos.isEmpty()) {
                                        for (Odontologo o : odontologos) {
                                %>
                                <tr>
                                    <td><%= o.getNombre() %></td>
                                    <td><%= o.getApellido() %></td>
                                    <td><%= o.getMatricula() %></td>
                                    <td><%= o.getEspecialidad() %></td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="4" class="text-center">No hay odontólogos asignados a este horario.</td>
                                </tr>
                                <% } %>
                                </tbody>
                            </table>
                        </div>

                        <a href="${pageContext.request.contextPath}/horarios" class="btn btn-secondary mt-3">
                            Volver al listado de horarios
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
    $(document).ready(function() {
        $('#dataTable').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.20/i18n/Spanish.json"
            }
        });
    });
</script>

</body>

</html>
