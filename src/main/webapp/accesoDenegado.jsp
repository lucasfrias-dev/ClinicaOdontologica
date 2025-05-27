<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="components/header.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Acceso Denegado</title>
</head>
<body class="bg-light">

<main class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-body text-center">
            <h1 class="text-danger">⛔ Acceso Denegado</h1>
            <p class="lead">No tienes permisos para acceder a esta página.</p>
            <p>Por favor, verifica con el administrador del sistema si necesitas acceso.</p>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary mt-3">Volver al Inicio</a>
        </div>
    </div>
</main>

</body>
</html>
