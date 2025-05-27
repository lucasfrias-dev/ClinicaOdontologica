<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../components/header.jsp" %>
<body id="page-top">
<div id="wrapper">
    <%@ include file="../../components/sidebar.jsp" %>

    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <%@ include file="../../components/navbar.jsp" %>

            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800">Registrar Odontólogo</h1>

                <div class="row">
                    <div class="col-lg-12">
                        <div class="alert alert-info" role="alert">
                            Completa el siguiente formulario para registrar un nuevo odontólogo.
                        </div>
                    </div>
                </div>

                <div class="row justify-content-center">
                    <div class="col-lg-10">
                        <div class="card shadow-lg border-0 rounded-lg">
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/odontologos/registrar" method="post" class="user">

                                    <!-- Datos personales -->
                                    <h5 class="text-gray-700 mb-3">Información personal</h5>
                                    <div class="form-group row">
                                        <div class="col-sm-6 mb-3">
                                            <input type="text" name="nombre" class="form-control form-control-user" placeholder="Nombre" required>
                                        </div>
                                        <div class="col-sm-6">
                                            <input type="text" name="apellido" class="form-control form-control-user" placeholder="Apellido" required>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-sm-4 mb-3">
                                            <input type="text" name="dni" class="form-control form-control-user" placeholder="DNI" required>
                                        </div>
                                        <div class="col-sm-4 mb-3">
                                            <input type="text" name="telefono" class="form-control form-control-user" placeholder="Teléfono">
                                        </div>
                                        <div class="col-sm-4">
                                            <input type="text" name="direccion" class="form-control form-control-user" placeholder="Dirección">
                                        </div>
                                        <div class="col-sm-4">
                                            <input type="date" name="fechaNacimiento" class="form-control form-control-user" required>
                                        </div>
                                    </div>

                                    <!-- Datos odontólogo -->
                                    <h5 class="text-gray-700 mt-4 mb-3">Datos profesionales</h5>
                                    <div class="form-group row">
                                        <div class="col-sm-6 mb-3">
                                            <input type="text" name="matricula" class="form-control form-control-user" placeholder="Matrícula" required>
                                        </div>
                                        <div class="col-sm-6">
                                            <input type="text" name="especialidad" class="form-control form-control-user" placeholder="Especialidad" required>
                                        </div>
                                    </div>

                                    <!-- Datos usuario -->
                                    <h5 class="text-gray-700 mt-4 mb-3">Cuenta de usuario</h5>
                                    <div class="form-group row">
                                        <div class="col-sm-6 mb-3">
                                            <input type="password" name="contrasenia" class="form-control form-control-user" placeholder="Contraseña" required>
                                        </div>
                                        <div class="col-sm-6">
                                            <select name="rol" class="form-control" required>
                                                <option value="">Seleccionar rol</option>
                                                <option value="ODONTOLOGO">Odontólogo</option>
                                            </select>
                                        </div>
                                    </div>

                                    <button type="submit" class="btn btn-primary btn-user btn-block">Registrar</button>
                                    <a href="${pageContext.request.contextPath}/odontologos" class="btn btn-secondary btn-user btn-block mt-2">Cancelar</a>+
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
</body>
</html>