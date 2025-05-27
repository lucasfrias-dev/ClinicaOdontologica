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
                <h1 class="h3 mb-4 text-gray-800">Editar Paciente</h1>
            </header>

            <!-- Mensajes -->
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
                            <form action="${pageContext.request.contextPath}/pacientes/editar" method="post" class="user">
                                <input type="hidden" name="idPaciente" value="${paciente.id}" />

                                <!-- Nombre -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkNombre" name="editarNombre">
                                    <label class="form-check-label" for="checkNombre">Editar nombre</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" id="nombre" name="nombre" class="form-control" value="${paciente.nombre}" readonly />
                                </div>

                                <!-- Apellido -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkApellido" name="editarApellido">
                                    <label class="form-check-label" for="checkApellido">Editar apellido</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" id="apellido" name="apellido" class="form-control" value="${paciente.apellido}" readonly />
                                </div>

                                <!-- DNI -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkDni" name="editarDni">
                                    <label class="form-check-label" for="checkDni">Editar DNI</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" id="dni" name="dni" class="form-control" value="${paciente.dni}" readonly />
                                </div>

                                <!-- Teléfono -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkTelefono" name="editarTelefono">
                                    <label class="form-check-label" for="checkTelefono">Editar teléfono</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" id="telefono" name="telefono" class="form-control" value="${paciente.telefono}" readonly />
                                </div>

                                <!-- Dirección -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkDireccion" name="editarDireccion">
                                    <label class="form-check-label" for="checkDireccion">Editar dirección</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" id="direccion" name="direccion" class="form-control" value="${paciente.direccion}" readonly />
                                </div>

                                <!-- Fecha de nacimiento -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkFechaNacimiento" name="editarFechaNacimiento">
                                    <label class="form-check-label" for="checkFechaNacimiento">Editar fecha de nacimiento</label>
                                </div>
                                <div class="form-group">
                                    <input type="date" id="fechaNacimiento" name="fechaNacimiento" class="form-control"
                                           value="${paciente.fechaNacimiento}" readonly />
                                </div>


                                <!-- Tipo de sangre -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkTipoSangre" name="editarTipoSangre">
                                    <label class="form-check-label" for="checkTipoSangre">Editar tipo de sangre</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" id="tipoSangre" name="tipoSangre" class="form-control" value="${paciente.tipoSangre}" readonly />
                                </div>

                                <!-- Obra social -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkObraSocial" name="editarObraSocial">
                                    <label class="form-check-label" for="checkObraSocial">Editar obra social</label>
                                </div>
                                <div class="form-group">
                                    <select name="obraSocial" id="obraSocial" class="form-control" disabled>
                                        <option value="true" ${paciente.obraSocial ? "selected" : ""}>Sí</option>
                                        <option value="false" ${!paciente.obraSocial ? "selected" : ""}>No</option>
                                    </select>
                                </div>

                                <!-- Responsable -->
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkResponsable" name="editarResponsable">
                                    <label class="form-check-label" for="checkResponsable">Editar responsable</label>
                                </div>
                                <div class="form-group">
                                    <select name="responsableId" id="responsableId" class="form-control" disabled>
                                        <c:forEach var="r" items="${responsables}">
                                            <option value="${r.id}" ${paciente.responsable.id == r.id ? "selected" : ""}>
                                                    ${r.nombre} ${r.apellido} - DNI: ${r.dni}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <button type="submit" class="btn btn-primary btn-user btn-block">Guardar cambios</button>
                                <a href="${pageContext.request.contextPath}/pacientes" class="btn btn-secondary btn-user btn-block mt-2">Cancelar</a>
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
    function toggleField(checkboxId, fieldId) {
        const checkbox = document.getElementById(checkboxId);
        const field = document.getElementById(fieldId);
        checkbox.addEventListener("change", function () {
            if (field.tagName === 'SELECT') {
                field.disabled = !this.checked;
            } else {
                field.readOnly = !this.checked;
            }
        });
    }

    toggleField("checkNombre", "nombre");
    toggleField("checkApellido", "apellido");
    toggleField("checkDni", "dni");
    toggleField("checkTelefono", "telefono");
    toggleField("checkDireccion", "direccion");
    toggleField("checkFechaNacimiento", "fechaNacimiento");
    toggleField("checkTipoSangre", "tipoSangre");
    toggleField("checkObraSocial", "obraSocial");
    toggleField("checkResponsable", "responsableId");
</script>
</body>
</html>
