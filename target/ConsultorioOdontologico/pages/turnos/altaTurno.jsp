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
                <h1 class="h3 mb-4 text-gray-800">Agendar Turno</h1>
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
                            <form action="${pageContext.request.contextPath}/turnos/agendar" method="post" class="user">

                                <!-- Paciente -->
                                <div class="form-group mb-3">
                                    <label for="pacienteId">Seleccionar paciente</label>
                                    <select id="pacienteId" name="pacienteId" class="form-control" required>
                                        <option value="">Seleccionar paciente</option>
                                        <c:forEach var="p" items="${pacientes}">
                                            <option value="${p.id}" ${pacienteIdParam == p.id ? 'selected' : ''}>
                                                    ${p.nombre} ${p.apellido} - DNI: ${p.dni}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Odontólogo -->
                                <div class="form-group mb-3">
                                    <label for="odontologoId">Seleccionar odontólogo</label>
                                    <select id="odontologoId" name="odontologoId" class="form-control" required>
                                        <option value="">Seleccionar odontólogo</option>
                                        <c:forEach var="o" items="${odontologos}">
                                            <option value="${o.id}">
                                                    ${o.nombre} ${o.apellido} - Matrícula: ${o.matricula}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Fecha y Hora -->
                                <div class="form-group mb-3">
                                    <label for="fechaHora">Seleccionar horario disponible</label>
                                    <select id="fechaHora" name="fechaHora" class="form-control" required>
                                        <option value="">Seleccione un odontólogo primero</option>
                                    </select>
                                </div>

                                <!-- Motivo o Afección -->
                                <div class="form-group mb-4">
                                    <label for="afeccion">Motivo de consulta</label>
                                    <textarea id="afeccion" name="afeccion" class="form-control" rows="4" placeholder="Describa brevemente el motivo del turno..." required></textarea>
                                </div>

                                <button type="submit" class="btn btn-primary btn-user btn-block">Agendar Turno</button>
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
    document.getElementById("odontologoId").addEventListener("change", function () {
        const odontologoId = this.value;
        const selectHorario = document.getElementById("fechaHora");

        // Limpiar opciones anteriores
        selectHorario.innerHTML = '<option value="">Cargando horarios...</option>';

        if (odontologoId) {
            fetch('${pageContext.request.contextPath}/api/odontologos/turnosDisponibles?odontologoId=' + odontologoId)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al obtener horarios');
                    }
                    return response.json();
                })
                .then(data => {
                    selectHorario.innerHTML = ''; // Limpiar opciones anteriores

                    if (!data || data.length === 0) {
                        selectHorario.innerHTML = '<option value="">No hay horarios disponibles</option>';
                    } else {
                        data.forEach(turno => {
                            const opcion = document.createElement("option");
                            // Ahora directamente el turno tiene un String en formato legible
                            const texto = turno.fechaHora;

                            opcion.value = texto;
                            opcion.text = texto;
                            selectHorario.appendChild(opcion);
                        });
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    selectHorario.innerHTML = '<option value="">Error al cargar horarios</option>';
                });
        } else {
            selectHorario.innerHTML = '<option value="">Seleccione un odontólogo primero</option>';
        }
    });

</script>
</body>
</html>
