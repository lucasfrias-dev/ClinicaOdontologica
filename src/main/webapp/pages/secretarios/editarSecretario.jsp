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
                <h1 class="h3 mb-4 text-gray-800">Editar Secretario</h1>
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
                            <form action="${pageContext.request.contextPath}/secretarios/editar" method="post" class="user">
                                <input type="hidden" name="idSecretario" value="${secretario.id}" />

                                <!-- Nombre y Apellido -->
                                <div class="form-check mb-2">
                                    <input type="checkbox" class="form-check-input" id="editarNombreCheck" name="editarNombreCheck">
                                    <label class="form-check-label" for="editarNombreCheck">Editar nombre y apellido</label>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3">
                                        <input type="text" name="nombre" id="nombre" class="form-control" value="${secretario.nombre}" readonly>
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="text" name="apellido" id="apellido" class="form-control" value="${secretario.apellido}" readonly>
                                    </div>
                                </div>

                                <!-- DNI y Teléfono -->
                                <div class="form-check mb-2">
                                    <input type="checkbox" class="form-check-input" id="editarContactoCheck" name="editarContactoCheck">
                                    <label class="form-check-label" for="editarContactoCheck">Editar DNI y Teléfono</label>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3">
                                        <input type="text" name="dni" id="dni" class="form-control" value="${secretario.dni}" readonly>
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="text" name="telefono" id="telefono" class="form-control" value="${secretario.telefono}" readonly>
                                    </div>
                                </div>

                                <!-- Dirección -->
                                <div class="form-check mb-2">
                                    <input type="checkbox" class="form-check-input" id="editarDireccionCheck" name="editarDireccionCheck">
                                    <label class="form-check-label" for="editarDireccionCheck">Editar dirección</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="direccion" id="direccion" class="form-control" value="${secretario.direccion}" readonly>
                                </div>

                                <!-- Fecha de Nacimiento -->
                                <div class="form-check mb-2">
                                    <input type="checkbox" class="form-check-input" id="editarFechaNacimientoCheck" name="editarFechaNacimientoCheck">
                                    <label class="form-check-label" for="editarFechaNacimientoCheck">Editar fecha de nacimiento</label>
                                </div>
                                <div class="form-group">
                                    <input type="date" name="fechaNacimiento" id="fechaNacimiento" class="form-control"
                                           value="${secretario.fechaNacimiento}" readonly>
                                </div>


                                <!-- Sector -->
                                <div class="form-check mb-2">
                                    <input type="checkbox" class="form-check-input" id="editarSectorCheck" name="editarSectorCheck">
                                    <label class="form-check-label" for="editarSectorCheck">Editar sector</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="sector" id="sector" class="form-control" value="${secretario.sector}" readonly>
                                </div>

                                <!-- Usuario (solo visualización, no editable desde acá) -->
                                <div class="form-group">
                                    <label for="usuario">Usuario asignado</label>
                                    <input type="text" id="usuario" class="form-control" value="${secretario.usuario.nombreUsuario}" readonly>
                                </div>

                                <button type="submit" class="btn btn-primary btn-user btn-block">Guardar cambios</button>
                                <a href="${pageContext.request.contextPath}/secretarios" class="btn btn-secondary btn-user btn-block mt-2">Cancelar</a>
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
    const toggleEditable = (checkboxId, fieldIds) => {
        document.getElementById(checkboxId).addEventListener("change", function () {
            fieldIds.forEach(id => {
                document.getElementById(id).readOnly = !this.checked;
                if (this.checked) document.getElementById(id).focus();
            });
        });
    };

    toggleEditable("editarNombreCheck", ["nombre", "apellido"]);
    toggleEditable("editarContactoCheck", ["dni", "telefono"]);
    toggleEditable("editarDireccionCheck", ["direccion"]);
    toggleEditable("editarFechaNacimientoCheck", ["fechaNacimiento"]);
    toggleEditable("editarSectorCheck", ["sector"]);
</script>
</body>
</html>
