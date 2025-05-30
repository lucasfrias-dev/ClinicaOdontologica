<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../../components/header.jsp" %>
<body id="page-top">
<div id="wrapper">
    <%@ include file="../../components/sidebar.jsp" %>

    <main id="content-wrapper" class="d-flex flex-column">
        <header>
            <%@ include file="../../components/navbar.jsp" %>
        </header>

        <section class="container-fluid">
            <header>
                <h1 class="h3 mb-4 text-gray-800">Editar Turno</h1>
            </header>

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

            <div class="row justify-content-center">
                <div class="col-lg-10">
                    <article class="card shadow-lg border-0 rounded-lg">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/turnos/editar" method="post" class="user">

                                <!-- ID del turno oculto -->
                                <input type="hidden" name="idTurno" value="${turno.idTurno}" />

                                <!-- Paciente (solo lectura) -->
                                <div class="form-group mb-3">
                                    <label>Paciente</label>
                                    <input type="text" class="form-control" value="${turno.paciente.nombre} ${turno.paciente.apellido}" readonly />
                                </div>

                                <!-- Odontólogo (solo lectura) -->
                                <div class="form-group mb-3">
                                    <label>Odontólogo</label>
                                    <input type="text" class="form-control" value="${turno.odontologo.nombre} ${turno.odontologo.apellido}" readonly />
                                </div>

                                <!-- Checkbox para editar motivo -->
                                <div class="form-check mb-2">
                                    <input class="form-check-input" type="checkbox" id="editarMotivoCheck" name="editarAfeccion" />
                                    <label class="form-check-label" for="editarMotivoCheck">Editar motivo de consulta</label>
                                </div>

                                <!-- Campo editable para el motivo -->
                                <div class="form-group mb-3">
                                    <label for="afeccion">Motivo de consulta</label>
                                    <textarea class="form-control" id="afeccion" name="afeccion" readonly>${turno.afeccion}</textarea>
                                </div>

                                <!-- Checkbox para editar fecha/hora -->
                                <div class="form-check mb-2">
                                    <input class="form-check-input" type="checkbox" id="editarFechaHoraCheck" name="editarFechaHora" />
                                    <label class="form-check-label" for="editarFechaHoraCheck">Editar fecha y hora</label>
                                </div>

                                <!-- Select de fecha/hora -->
                                <div class="form-group mb-3">
                                    <label for="fechaHora">Seleccionar nuevo horario</label>
                                    <select id="fechaHora" name="fechaHora" class="form-control" disabled>
                                        <!-- Mostrar el turno actual como opción seleccionada -->
                                        <option value="${fechaTurnoActual}" selected>
                                            Turno actual: ${fechaTurnoActual}
                                        </option>

                                        <!-- Luego, los nuevos turnos disponibles -->
                                        <c:forEach var="horario" items="${turnosDisponibles}">
                                            <option value="${horario.fechaHora}">
                                                    ${horario.fechaHora}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>



                                <button type="submit" class="btn btn-primary btn-user btn-block">Guardar cambios</button>
                                <a href="${pageContext.request.contextPath}/turnos" class="btn btn-secondary btn-user btn-block mt-2">Cancelar</a>
                            </form>
                        </div>
                    </article>
                </div>
            </div>
        </section>

        <footer>
            <%@ include file="../../components/footer.jsp" %>
        </footer>
    </main>
</div>
<%@ include file="../../components/scripts.jsp" %>
<script>
    // Habilitar/deshabilitar edición del motivo
    document.getElementById("editarMotivoCheck").addEventListener("change", function () {
        const textarea = document.getElementById("afeccion");
        textarea.readOnly = !this.checked;
        if (this.checked) {
            textarea.focus();
        }
    });

    // Habilitar/deshabilitar edición de fecha y hora
    document.getElementById("editarFechaHoraCheck").addEventListener("change", function () {
        const select = document.getElementById("fechaHora");
        select.disabled = !this.checked;
    });
</script>
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
