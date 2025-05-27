<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../components/header.jsp" %>
<%@ page import="org.lucasf.consultorioodontologico.models.entities.Usuario" %>
<%@ page import="org.lucasf.consultorioodontologico.models.enums.Rol" %>
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
                <h1 class="h3 mb-2 text-gray-800">Listado de Turnos</h1>
                <p class="mb-4">Administración de los turnos registrados en el sistema.</p>

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

                <% if (!esOdontologo) { %>
                <a href="${pageContext.request.contextPath}/turnos/agendar" class="btn btn-success btn-sm mb-4">Agendar nuevo turno</a>
                <% } %>

                <!-- Turnos Futuros -->
                <h4 class="mb-3 text-success">Turnos Futuros</h4>
                <jsp:include page="tablaTurnos.jsp">
                    <jsp:param name="nombreLista" value="turnosFuturos"/>
                </jsp:include>

                <!-- Turnos Pasados -->
                <h4 class="mt-5 mb-3 text-secondary">Turnos Pasados</h4>
                <jsp:include page="tablaTurnos.jsp">
                    <jsp:param name="nombreLista" value="turnosPasados"/>
                </jsp:include>

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
        $('.dataTableTurnos').DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.20/i18n/Spanish.json"
            },
            "pageLength": 10,
            "lengthMenu": [[10, 25, 50, 100], [10, 25, 50, 100]],
            "columnDefs": [
                { "orderable": false, "targets": -1 }
            ]
        });
    });
</script>

</body>
</html>
