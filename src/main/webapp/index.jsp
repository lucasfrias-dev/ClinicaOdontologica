<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="components/header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<body id="page-top">
<div id="wrapper">
    <%@ include file="components/sidebar.jsp" %>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <%@ include file="components/navbar.jsp" %>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800">Bienvenido al Sistema de Gestión Odontológica</h1>

                <c:choose>
                    <c:when test="${sessionScope.usuario.rol == 'ADMIN'}">
                        <p>Usted tiene acceso completo como <strong>Administrador</strong>.</p>
                        <p>Puede registrar, editar y consultar odontólogos, pacientes, secretarios, turnos y horarios.</p>
                    </c:when>
                    <c:when test="${sessionScope.usuario.rol == 'SECRETARIO'}">
                        <p>Usted tiene acceso como <strong>Secretario</strong>.</p>
                        <p>Puede consultar información general, agendar turnos y asignar horarios. No puede registrar usuarios nuevos.</p>
                    </c:when>
                    <c:when test="${sessionScope.usuario.rol == 'ODONTOLOGO'}">
                        <p>Usted tiene acceso como <strong>Odontólogo</strong>.</p>
                        <p>Puede consultar sus turnos y horarios asignados.</p>
                    </c:when>
                    <c:otherwise>
                        <p>No se pudo determinar su rol. Por favor, contacte al administrador.</p>
                    </c:otherwise>
                </c:choose>

                <p>Seleccione una opción del menú para comenzar.</p>
            </div>

        </div>
        <%@ include file="components/footer.jsp" %>
    </div>
</div>
<%@ include file="components/scripts.jsp" %>
</body>
</html>
