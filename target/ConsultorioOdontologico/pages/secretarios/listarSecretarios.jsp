<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../components/header.jsp" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Odontologo" %>
<%@ page import="java.util.List" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Secretario" %>
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
                <h1 class="h3 mb-2 text-gray-800">Listado de Secretarios</h1>
                <p class="mb-4">Administración de secretarios registrados en el sistema.</p>

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

                <!-- DataTales Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Secretarios</h6>
                        <a href="${pageContext.request.contextPath}/secretarios/registrar" class="btn btn-success btn-sm float-right">Registrar nuevo secretario</a>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Sector</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Sector</th>
                                    <th>Acciones</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <%
                                    List<Secretario> secretarios = (List<Secretario>) request.getAttribute("secretarios");
                                    if (secretarios != null && !secretarios.isEmpty()) {
                                        for (Secretario s : secretarios) {
                                %>
                                <tr>
                                    <td><%= s.getId() %></td>
                                    <td><%= s.getNombre() %></td>
                                    <td><%= s.getApellido() %></td>
                                    <td><%= s.getSector() %></td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/secretarios/editar?idSecretario=<%= s.getId() %>" class="btn btn-sm btn-primary">
                                                <i class="fas fa-edit"></i> Editar
                                            </a>
                                            <form method="post" action="${pageContext.request.contextPath}/secretarios/eliminar" style="display:inline;">
                                                <input type="hidden" name="idSecretario" value="<%= s.getId() %>"/>
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('¿Está seguro que desea eliminar este secretario?');">
                                                    <i class="fas fa-trash"></i> Eliminar
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="6" class="text-center">No hay secretarios registrados.</td>
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