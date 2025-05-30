<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../components/header.jsp" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Horario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

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
                <h1 class="h3 mb-2 text-gray-800">Listado de Horarios</h1>
                <p class="mb-4">Administración de horarios disponibles para los odontólogos.</p>

                <!-- Mensajes de éxito o error -->
                <%
                    String mensajeExito = (String) request.getAttribute("mensajeExito");
                    String mensajeError = (String) request.getAttribute("mensajeError");
                %>

                <% if (mensajeExito != null) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <%= mensajeExito %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Cerrar">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <% } %>

                <% if (mensajeError != null) { %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <%= mensajeError %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Cerrar">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <% } %>

                <!-- DataTables Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Horarios</h6>
                        <a href="${pageContext.request.contextPath}/horarios/asignar" class="btn btn-success btn-sm float-right">
                            Asignar horario a odontólogo
                        </a>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Día</th>
                                    <th>Hora de Inicio</th>
                                    <th>Hora de Fin</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>Día</th>
                                    <th>Hora de Inicio</th>
                                    <th>Hora de Fin</th>
                                    <th>Acciones</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <%
                                    List<Horario> horarios = (List<Horario>) request.getAttribute("horarios");
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                                    if (horarios != null && !horarios.isEmpty()) {
                                        for (Horario h : horarios) {
                                %>
                                <tr>
                                    <td><%= h.getDia() != null ? h.getDia().toString() : "" %></td>
                                    <td><%= h.getHoraInicio() != null ? h.getHoraInicio().format(formatter) : "" %></td>
                                    <td><%= h.getHoraFin() != null ? h.getHoraFin().format(formatter) : "" %></td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/horarios/editar?idHorario=<%= h.getIdHorario() %>" class="btn btn-sm btn-primary">
                                                <i class="fas fa-edit"></i> Editar
                                            </a>
                                            <%--<form method="post" action="${pageContext.request.contextPath}/horarios/eliminar" style="display:inline;">
                                                <input type="hidden" name="idHorario" value="<%= h.getIdHorario() %>"/>
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('¿Está seguro que desea eliminar este horario? Tenga en cuenta que se eliminaran también los turnos en estos horarios.');">
                                                    <i class="fas fa-trash"></i> Eliminar
                                                </button>
                                            </form>--%>
                                            <a href="${pageContext.request.contextPath}/horarios/odontologos?idHorario=<%= h.getIdHorario() %>" class="btn btn-sm btn-info">
                                                <i class="fas fa-user-md"></i> Ver Odontólogos
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="4" class="text-center">No hay horarios registrados.</td>
                                </tr>
                                <% } %>
                                </tbody>
                            </table>
                        </div>
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

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<%@ include file="../../components/scripts.jsp" %>

<!-- Page level plugins -->
<script src="../../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
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
