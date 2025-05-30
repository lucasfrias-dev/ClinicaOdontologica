<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Horario" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Odontologo" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%@ include file="../../components/header.jsp" %>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">
    <%@ include file="../../components/sidebar.jsp" %>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">
            <%@ include file="../../components/navbar.jsp" %>

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <h1 class="h3 mb-2 text-gray-800">Horarios de Odontólogo</h1>
                <%
                    Odontologo odontologo = (Odontologo) request.getAttribute("odontologo");
                    Set<Horario> horarios = (Set<Horario>) request.getAttribute("horarios");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                %>
                <p class="mb-4">
                    Listado de horarios asignados al odontólogo
                    <strong><%= odontologo.getNombre() %> <%= odontologo.getApellido() %></strong>.
                </p>

                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Horarios</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Día</th>
                                    <th>Hora de Inicio</th>
                                    <th>Hora de Fin</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>Día</th>
                                    <th>Hora de Inicio</th>
                                    <th>Hora de Fin</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <%
                                    if (horarios != null && !horarios.isEmpty()) {
                                        for (Horario h : horarios) {
                                %>
                                <tr>
                                    <td><%= h.getDia() != null ? h.getDia().toString() : "" %></td>
                                    <td><%= h.getHoraInicio() != null ? h.getHoraInicio().format(formatter) : "" %></td>
                                    <td><%= h.getHoraFin() != null ? h.getHoraFin().format(formatter) : "" %></td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="3" class="text-center">No tiene horarios asignados.</td>
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
            <!-- /.container-fluid -->

        </div>
        <!-- End of Main Content -->

        <%@ include file="../../components/footer.jsp" %>

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

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
