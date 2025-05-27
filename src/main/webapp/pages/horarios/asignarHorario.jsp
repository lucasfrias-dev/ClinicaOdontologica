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
                <h1 class="h3 mb-4 text-gray-800">Asignar Horario a Odontólogo</h1>
            </header>

            <div class="row">
                <!-- Mensajes de éxito -->
                <c:if test="${param.exito == '1'}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        Horario asignado correctamente.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
                    </div>
                </c:if>

                <!-- Mensajes de error desde request -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
                    </div>
                </c:if>

                <div class="col-lg-12">
                    <div class="alert alert-info" role="alert">
                        Seleccioná un odontólogo, elegí un horario ya existente o creá uno nuevo y asignáselo.
                    </div>
                </div>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-10">
                    <article class="card shadow-lg border-0 rounded-lg">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/horarios/asignar" method="post" class="user">

                                <!-- Odontólogo -->
                                <section aria-labelledby="odontologo-section">
                                    <h2 id="odontologo-section" class="text-gray-700 mb-3">Odontólogo</h2>
                                    <div class="form-group">
                                        <label for="odontologoId">Seleccionar odontólogo</label>
                                        <select id="odontologoId" name="odontologoId" class="form-control" required>
                                            <option value="">Seleccionar odontólogo</option>
                                            <c:if test="${noOdontologos}">
                                                <option value="" disabled>No hay odontólogos disponibles</option>
                                            </c:if>
                                            <c:forEach var="o" items="${odontologos}">
                                                <option value="${o.id}">
                                                        ${o.nombre} ${o.apellido} - Matrícula: ${o.matricula}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </section>

                                <!-- Horario existente -->
                                <section aria-labelledby="horario-existente-section">
                                    <h2 id="horario-existente-section" class="text-gray-700 mt-4 mb-3">Horario existente</h2>
                                    <div class="form-group">
                                        <label for="horarioId">Seleccionar horario existente</label>
                                        <select id="horarioId" name="horarioId" class="form-control">
                                            <option value="">-- Seleccionar un horario ya creado --</option>
                                            <c:if test="${noHorarios}">
                                                <option value="" disabled>No hay horarios disponibles</option>
                                            </c:if>
                                            <c:forEach var="h" items="${horarios}">
                                                <option value="${h.idHorario}">
                                                        ${h.dia} - ${h.horaInicio} a ${h.horaFin}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </section>

                                <div class="form-check mb-3">
                                    <input class="form-check-input" type="checkbox" id="crearNuevoCheck" name="crearNuevo">
                                    <label class="form-check-label" for="crearNuevoCheck">
                                        Crear un nuevo horario
                                    </label>
                                </div>


                                <!-- Crear nuevo horario -->
                                <section aria-labelledby="nuevo-horario-section" id="crearHorarioSection" style="display: none;">
                                    <h2 id="nuevo-horario-section" class="text-gray-700 mt-4 mb-3">Nuevo horario</h2>
                                    <div class="form-group row">
                                        <div class="col-sm-4 mb-3">
                                            <label for="dia">Día</label>
                                            <select id="dia" name="dia" class="form-control"
                                                    style="appearance: auto; padding: .375rem .75rem;">
                                                <option value="">Seleccionar día</option>
                                                <option value="LUNES">Lunes</option>
                                                <option value="MARTES">Martes</option>
                                                <option value="MIERCOLES">Miércoles</option>
                                                <option value="JUEVES">Jueves</option>
                                                <option value="VIERNES">Viernes</option>
                                                <option value="SABADO">Sábado</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-4 mb-3">
                                            <label for="horaInicio">Hora inicio</label>
                                            <input type="time" id="horaInicio" name="horaInicio" class="form-control form-control-user">
                                        </div>
                                        <div class="col-sm-4 mb-3">
                                            <label for="horaFin">Hora fin</label>
                                            <input type="time" id="horaFin" name="horaFin" class="form-control form-control-user">
                                        </div>
                                    </div>
                                </section>


                                <button type="submit" class="btn btn-primary btn-user btn-block">Asignar Horario</button>
                                <a href="${pageContext.request.contextPath}/odontologos" class="btn btn-secondary btn-user btn-block mt-2">Cancelar</a>
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
    document.getElementById("crearNuevoCheck").addEventListener("change", function () {
        const crearHorarioSection = document.getElementById("crearHorarioSection");
        crearHorarioSection.style.display = this.checked ? "block" : "none";

        // También podés limpiar los campos si se desactiva:
        if (!this.checked) {
            document.getElementById("dia").value = "";
            document.getElementById("horaInicio").value = "";
            document.getElementById("horaFin").value = "";
        }
    });
</script>

</body>
</html>
