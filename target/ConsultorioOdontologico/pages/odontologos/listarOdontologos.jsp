<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../components/header.jsp" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Odontologo" %>
<%@ page import="java.util.List" %>
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
                <h1 class="h3 mb-2 text-gray-800">Listado de Odontólogos</h1>
                <p class="mb-4">Administración de odontólogos registrados en el sistema.</p>

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
                        <h6 class="m-0 font-weight-bold text-primary">Odontólogos</h6>
                        <a href="${pageContext.request.contextPath}/odontologos/registrar" class="btn btn-success btn-sm float-right">Registrar nuevo odontólogo</a>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Matrícula</th>
                                    <th>Especialidad</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Matrícula</th>
                                    <th>Especialidad</th>
                                    <th>Acciones</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <%
                                    List<Odontologo> odontologos = (List<Odontologo>) request.getAttribute("odontologos");
                                    if (odontologos != null && !odontologos.isEmpty()) {
                                        for (Odontologo o : odontologos) {
                                %>
                                <tr>
                                    <td><%= o.getId() %></td>
                                    <td><%= o.getNombre() %></td>
                                    <td><%= o.getApellido() %></td>
                                    <td><%= o.getMatricula() %></td>
                                    <td><%= o.getEspecialidad() %></td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/odontologos/editar?idOdontologo=<%= o.getId() %>" class="btn btn-sm btn-primary">
                                                <i class="fas fa-edit"></i> Editar
                                            </a>
                                            <form method="post" action="${pageContext.request.contextPath}/odontologos/eliminar" style="display:inline;">
                                                <input type="hidden" name="idOdontologo" value="<%= o.getId() %>"/>
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('¿Está seguro que desea eliminar este odontólogo?');">
                                                    <i class="fas fa-trash"></i> Eliminar
                                                </button>
                                            </form>
                                            <a href="${pageContext.request.contextPath}/odontologos/horarios?idOdontologo=<%= o.getId() %>" class="btn btn-sm btn-info">
                                                <i class="fas fa-calendar-alt"></i> Horarios
                                            </a>
                                            <a href="${pageContext.request.contextPath}/odontologos/turnos?idOdontologo=<%= o.getId() %>" class="btn btn-sm btn-secondary">
                                                <i class="fas fa-list"></i> Turnos
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="6" class="text-center">No hay odontólogos registrados.</td>
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