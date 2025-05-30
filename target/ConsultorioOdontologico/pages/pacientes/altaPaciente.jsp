<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../../components/header.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Registrar Paciente</title>
    <script>
        function toggleResponsableFields() {
            const edadInput = document.getElementById('edad');
            const responsableSection = document.getElementById('responsableSection');

            if (edadInput.value < 18) {
                responsableSection.style.display = 'block';
                document.querySelectorAll('#responsableSection input').forEach(input => {
                    input.required = true;
                });
            } else {
                responsableSection.style.display = 'none';
                document.querySelectorAll('#responsableSection input').forEach(input => {
                    input.required = false;
                });
            }
        }

        function handleDesconocido(selectElement) {
            const tipoSangreInput = document.getElementById('tipoSangreInput');
            if (selectElement.value === 'DESCONOCIDO') {
                tipoSangreInput.style.display = 'none';
                document.getElementById('tipoSangre').value = 'DESCONOCIDO';
            } else {
                tipoSangreInput.style.display = 'block';
            }
        }
    </script>
</head>
<body id="page-top">
<div id="wrapper">
    <%@ include file="../../components/sidebar.jsp" %>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <%@ include file="../../components/navbar.jsp" %>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800">Registrar Paciente</h1>

                <!-- Mensajes de error -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
                    </div>
                </c:if>

                <div class="row justify-content-center">
                    <div class="col-lg-10">
                        <div class="card shadow-lg border-0 rounded-lg">
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/pacientes/registrar" method="post" class="needs-validation" novalidate>

                                    <!-- Sección Datos Personales -->
                                    <section aria-labelledby="datosPersonales" class="mb-4">
                                        <h2 id="datosPersonales" class="h5 text-gray-700 mb-3">Datos Personales</h2>
                                        <div class="form-group row">
                                            <div class="col-md-6 mb-3">
                                                <label for="nombre">Nombre</label>
                                                <input type="text" id="nombre" name="nombre" class="form-control" required>
                                                <div class="invalid-feedback">Por favor ingrese el nombre</div>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="apellido">Apellido</label>
                                                <input type="text" id="apellido" name="apellido" class="form-control" required>
                                                <div class="invalid-feedback">Por favor ingrese el apellido</div>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <div class="col-md-4 mb-3">
                                                <label for="dni">DNI</label>
                                                <input type="text" id="dni" name="dni" class="form-control" required>
                                                <div class="invalid-feedback">Por favor ingrese el DNI</div>
                                            </div>
                                            <div class="col-md-4 mb-3">
                                                <label for="telefono">Teléfono</label>
                                                <input type="tel" id="telefono" name="telefono" class="form-control" required>
                                                <div class="invalid-feedback">Por favor ingrese el teléfono</div>
                                            </div>
                                            <div class="col-md-4 mb-3">
                                                <label for="edad">Edad</label>
                                                <input type="number" id="edad" name="edad" class="form-control" required min="0" max="120" onchange="toggleResponsableFields()">
                                                <div class="invalid-feedback">Por favor ingrese una edad válida</div>
                                            </div>
                                        </div>

                                        <div class="form-group mb-3">
                                            <label for="direccion">Dirección</label>
                                            <input type="text" id="direccion" name="direccion" class="form-control" required>
                                            <div class="invalid-feedback">Por favor ingrese la dirección</div>
                                        </div>
                                    </section>

                                    <!-- Sección Datos Médicos -->
                                    <section aria-labelledby="datosMedicos" class="mb-4">
                                        <h2 id="datosMedicos" class="h5 text-gray-700 mb-3">Datos Médicos</h2>
                                        <div class="form-group row">
                                            <div class="col-md-6 mb-3">
                                                <label for="tipoSangre">Tipo de Sangre</label>
                                                <div class="input-group">
                                                    <select id="tipoSangre" name="tipoSangre" class="form-control" required
                                                            onchange="handleDesconocido(this)">
                                                        <option value="">Seleccione tipo de sangre</option>
                                                        <option value="A+">A+</option>
                                                        <option value="A-">A-</option>
                                                        <option value="B+">B+</option>
                                                        <option value="B-">B-</option>
                                                        <option value="AB+">AB+</option>
                                                        <option value="AB-">AB-</option>
                                                        <option value="O+">O+</option>
                                                        <option value="O-">O-</option>
                                                        <option value="DESCONOCIDO">Desconocido</option>
                                                    </select>
                                                </div>
                                                <div class="invalid-feedback">Por favor seleccione el tipo de sangre</div>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <div class="form-check mt-4 pt-2">
                                                    <input class="form-check-input" type="checkbox" id="obraSocial" name="obraSocial">
                                                    <label class="form-check-label" for="obraSocial">
                                                        ¿Tiene obra social?
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </section>

                                    <!-- Sección Responsable (condicional) -->
                                    <section id="responsableSection" aria-labelledby="responsableTitle" class="mb-4" style="display: none;">
                                        <h2 id="responsableTitle" class="h5 text-gray-700 mb-3">Datos del Responsable</h2>
                                        <div class="alert alert-info">
                                            <small>Complete los datos del responsable (padre, madre o tutor) ya que el paciente es menor de edad</small>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-md-6 mb-3">
                                                <label for="responsableNombre">Nombre del Responsable</label>
                                                <input type="text" id="responsableNombre" name="responsableNombre" class="form-control">
                                                <div class="invalid-feedback">Por favor ingrese el nombre del responsable</div>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="responsableApellido">Apellido del Responsable</label>
                                                <input type="text" id="responsableApellido" name="responsableApellido" class="form-control">
                                                <div class="invalid-feedback">Por favor ingrese el apellido del responsable</div>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-md-6 mb-3">
                                                <label for="responsableDni">DNI del Responsable</label>
                                                <input type="text" id="responsableDni" name="responsableDni" class="form-control">
                                                <div class="invalid-feedback">Por favor ingrese el DNI del responsable</div>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="responsableTelefono">Teléfono del Responsable</label>
                                                <input type="tel" id="responsableTelefono" name="responsableTelefono" class="form-control">
                                                <div class="invalid-feedback">Por favor ingrese el teléfono del responsable</div>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="responsableDireccion">Dirección del Responsable</label>
                                                <input type="text" id="responsableDireccion" name="responsableDireccion" class="form-control">
                                                <div class="invalid-feedback">Por favor ingrese la dirección del responsable</div>
                                            </div>
                                        </div>
                                        <div class="form-group mb-3">
                                            <label for="responsableParentesco">Parentesco</label>
                                            <input type="text" id="responsableParentesco" name="responsableParentesco" class="form-control" placeholder="Ej: Padre, Madre, Tutor">
                                            <div class="invalid-feedback">Por favor ingrese el parentesco</div>
                                        </div>
                                    </section>

                                    <!-- Sección Datos Desconocidos -->
                                    <section aria-labelledby="datosDesconocidos" class="mb-4">
                                        <h2 id="datosDesconocidos" class="h5 text-gray-700 mb-3">Datos no disponibles</h2>
                                        <div class="alert alert-warning">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="datosDesconocidosCheck" name="datosDesconocidos">
                                                <label class="form-check-label" for="datosDesconocidosCheck">
                                                    Marque esta casilla si desconoce alguno de los datos solicitados.
                                                    Podrán completarse más adelante.
                                                </label>
                                            </div>
                                        </div>
                                    </section>

                                    <div class="d-flex justify-content-between mt-4">
                                        <a href="${pageContext.request.contextPath}/pacientes" class="btn btn-secondary">Cancelar</a>
                                        <button type="submit" class="btn btn-primary">Registrar Paciente</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../../components/footer.jsp" %>
    </div>
</div>
<%@ include file="../../components/scripts.jsp" %>

<script>
    // Validación del formulario
    (function() {
        'use strict';
        window.addEventListener('load', function() {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function(form) {
                form.addEventListener('submit', function(event) {
                    // Si marca datos desconocidos, ignorar validación
                    if(document.getElementById('datosDesconocidosCheck').checked) {
                        return true;
                    }

                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();

    // Inicializar la visibilidad de la sección de responsable al cargar
    document.addEventListener('DOMContentLoaded', function() {
        toggleResponsableFields();

        // Configurar el manejador de "desconocido" para tipo de sangre
        const tipoSangreSelect = document.getElementById('tipoSangre');
        if (tipoSangreSelect) {
            handleDesconocido(tipoSangreSelect);
        }
    });
</script>
</body>
</html>