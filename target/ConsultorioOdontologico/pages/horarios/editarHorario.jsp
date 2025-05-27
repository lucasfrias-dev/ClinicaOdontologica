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
                <h1 class="h3 mb-4 text-gray-800">Editar Horario</h1>
            </header>

            <%-- Mensajes de éxito o error --%>
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
                <div class="col-lg-8">
                    <article class="card shadow-lg border-0 rounded-lg">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/horarios/editar" method="post" class="user">

                                <!-- ID oculto -->
                                <input type="hidden" name="idHorario" value="${horario.idHorario}" />

                                <!-- Checkbox y campo Día -->
                                <div class="form-check mb-2">
                                    <input class="form-check-input" type="checkbox" id="editarDiaCheck" name="editarDia" />
                                    <label class="form-check-label" for="editarDiaCheck">Editar día</label>
                                </div>
                                <div class="form-group mb-3">
                                    <label for="dia">Día de la semana</label>
                                    <select id="dia" name="dia" class="form-control" disabled>
                                        <c:forEach var="diaItem" items="${diasSemana}">
                                            <option value="${diaItem}" ${diaItem == horario.dia ? 'selected' : ''}>${diaItem}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Checkbox y campo Hora Inicio -->
                                <div class="form-check mb-2">
                                    <input class="form-check-input" type="checkbox" id="editarInicioCheck" name="editarInicio" />
                                    <label class="form-check-label" for="editarInicioCheck">Editar hora de inicio</label>
                                </div>
                                <div class="form-group mb-3">
                                    <label for="horaInicio">Hora de inicio</label>
                                    <input type="time" id="horaInicio" name="horaInicio" class="form-control" value="${horario.horaInicio}" disabled />
                                </div>

                                <!-- Checkbox y campo Hora Fin -->
                                <div class="form-check mb-2">
                                    <input class="form-check-input" type="checkbox" id="editarFinCheck" name="editarFin" />
                                    <label class="form-check-label" for="editarFinCheck">Editar hora de fin</label>
                                </div>
                                <div class="form-group mb-3">
                                    <label for="horaFin">Hora de fin</label>
                                    <input type="time" id="horaFin" name="horaFin" class="form-control" value="${horario.horaFin}" disabled />
                                </div>

                                <button type="submit" class="btn btn-primary btn-user btn-block">Guardar cambios</button>
                                <a href="${pageContext.request.contextPath}/horarios" class="btn btn-secondary btn-user btn-block mt-2">Cancelar</a>
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

<!-- JS para habilitar/deshabilitar campos -->
<script>
    document.getElementById("editarDiaCheck").addEventListener("change", function () {
        document.getElementById("dia").disabled = !this.checked;
    });

    document.getElementById("editarInicioCheck").addEventListener("change", function () {
        document.getElementById("horaInicio").disabled = !this.checked;
    });

    document.getElementById("editarFinCheck").addEventListener("change", function () {
        document.getElementById("horaFin").disabled = !this.checked;
    });
</script>
</body>
</html>
