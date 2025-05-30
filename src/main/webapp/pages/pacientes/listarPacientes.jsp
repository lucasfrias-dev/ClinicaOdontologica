<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../components/header.jsp" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Paciente" %>
<%@ page import="java.util.List" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Usuario" %>
<%@ page import="org.lucasf.consultorioodontologico.models.Rol" %>
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
                <h1 class="h3 mb-2 text-gray-800">Listado de Pacientes</h1>
                <p class="mb-4">Administración de pacientes registrados en el sistema.</p>

                <!-- Mensajes de éxito o error -->
                <%
                    String mensajeExito = (String) request.getAttribute("mensajeExito");
                    String mensajeError = (String) request.getAttribute("mensajeError");
                    Usuario usuario = (Usuario) session.getAttribute("usuario");
                    boolean esOdontologo = usuario != null && usuario.getRol() == Rol.ODONTOLOGO;
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
                        <h6 class="m-0 font-weight-bold text-primary">Pacientes</h6>
                        <% if (!esOdontologo) { %>
                        <a href="${pageContext.request.contextPath}/pacientes/registrar" class="btn btn-success btn-sm float-right">
                            <i class="fas fa-plus"></i> Registrar nuevo paciente
                        </a>
                        <% } %>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>DNI</th>
                                    <th>Teléfono</th>
                                    <th>Obra Social</th>
                                    <th>Responsable</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>DNI</th>
                                    <th>Teléfono</th>
                                    <th>Obra Social</th>
                                    <th>Responsable</th>
                                    <th>Acciones</th>
                                </tr>
                                </tfoot>
                                <tbody>
                                <%
                                    List<Paciente> pacientes = (List<Paciente>) request.getAttribute("pacientes");
                                    if (pacientes != null && !pacientes.isEmpty()) {
                                        for (Paciente p : pacientes) {
                                %>
                                <tr>
                                    <td><%= p.getId() %></td>
                                    <td><%= p.getNombre() %></td>
                                    <td><%= p.getApellido() %></td>
                                    <td><%= p.getDni() %></td>
                                    <td><%= p.getTelefono() %></td>
                                    <td class="text-center">
                                        <% if(p.isObraSocial()) { %>
                                        <span class="badge badge-success">Sí</span>
                                        <% } else { %>
                                        <span class="badge badge-secondary">No</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <% if(p.getResponsable() != null) { %>
                                        <%= p.getResponsable().getNombre() %> <%= p.getResponsable().getApellido() %>
                                        <% } else { %>
                                        <span class="text-muted">No aplica</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/pacientes/editar?idPaciente=<%= p.getId() %>"
                                               class="btn btn-sm btn-primary">
                                                <i class="fas fa-edit"></i> Editar
                                            </a>
                                            <form method="post" action="${pageContext.request.contextPath}/pacientes/eliminar" style="display:inline;">
                                                <input type="hidden" name="idPaciente" value="<%= p.getId() %>"/>
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('¿Está seguro que desea eliminar este paciente?');">
                                                    <i class="fas fa-trash"></i> Eliminar
                                                </button>
                                            </form>
                                            <a href="${pageContext.request.contextPath}/turnos/agendar?pacienteId=<%= p.getId() %>"
                                               class="btn btn-sm btn-secondary">
                                                <i class="fas fa-calendar-plus"></i> Nuevo Turno
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="8" class="text-center">No hay pacientes registrados.</td>
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
<script src="${pageContext.request.contextPath}/vendor/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script>
    $(document).ready(function() {
        $('#dataTable').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.20/i18n/Spanish.json"
            },
            "pageLength": 10,  // Número de registros por página por defecto
            "lengthMenu": [ // Opciones para el número de registros por página
                [10, 25, 50, 100],  // Opciones disponibles
                [10, 25, 50, 100]   // Etiquetas de las opciones
            ],
            "columnDefs": [
                { "orderable": false, "targets": [7] } // Deshabilitar ordenación en columna de acciones
            ]
        });
    });
</script>

</body>
</html>