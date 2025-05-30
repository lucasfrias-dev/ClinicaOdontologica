<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Marca del sistema -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/index.jsp">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-tooth"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Clínica Dental</div>
    </a>

    <hr class="sidebar-divider my-0">

    <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">
            <i class="fas fa-home"></i>
            <span>Inicio</span>
        </a>
    </li>

    <hr class="sidebar-divider">

    <!-- Sección: Gestión -->
    <div class="sidebar-heading">Gestión</div>

    <!-- Odontólogos -->
    <c:if test="${usuario.rol != 'ODONTOLOGO'}">
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOdontologos"
               aria-expanded="true" aria-controls="collapseOdontologos">
                <i class="fas fa-user-md"></i>
                <span>Odontólogos</span>
            </a>
            <div id="collapseOdontologos" class="collapse" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Opciones:</h6>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/odontologos">Ver Odontólogos</a>
                    <c:if test="${usuario.rol == 'ADMIN'}">
                        <a class="collapse-item" href="${pageContext.request.contextPath}/odontologos/registrar">Registrar Odontólogo</a>
                    </c:if>
                </div>
            </div>
        </li>
    </c:if>

    <!-- Pacientes -->
    <c:choose>
        <c:when test="${usuario.rol == 'ODONTOLOGO'}">
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePacientes"
                   aria-expanded="true" aria-controls="collapsePacientes">
                    <i class="fas fa-user-injured"></i>
                    <span>Mis Pacientes</span>
                </a>
                <div id="collapsePacientes" class="collapse" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">Opciones:</h6>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/pacientes">Ver Pacientes</a>
                    </div>
                </div>
            </li>
        </c:when>
        <c:otherwise>
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePacientes"
                   aria-expanded="true" aria-controls="collapsePacientes">
                    <i class="fas fa-user-injured"></i>
                    <span>Pacientes</span>
                </a>
                <div id="collapsePacientes" class="collapse" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">Opciones:</h6>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/pacientes">Ver Pacientes</a>
                        <c:if test="${usuario.rol == 'ADMIN' || usuario.rol == 'SECRETARIO'}">
                            <a class="collapse-item" href="${pageContext.request.contextPath}/pacientes/registrar">Registrar Paciente</a>
                        </c:if>
                    </div>
                </div>
            </li>
        </c:otherwise>
    </c:choose>

    <!-- Secretarios -->
    <c:if test="${usuario.rol == 'ADMIN'}">
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSecretarios"
               aria-expanded="true" aria-controls="collapseSecretarios">
                <i class="fas fa-user-tie"></i>
                <span>Secretarios</span>
            </a>
            <div id="collapseSecretarios" class="collapse" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Opciones:</h6>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/secretarios">Ver Secretarios</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/secretarios/registrar">Registrar Secretario</a>
                </div>
            </div>
        </li>
    </c:if>

    <!-- Turnos -->
    <c:choose>
        <c:when test="${usuario.rol == 'ODONTOLOGO'}">
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTurnos"
                   aria-expanded="true" aria-controls="collapseTurnos">
                    <i class="fas fa-calendar-check"></i>
                    <span>Mis Turnos</span>
                </a>
                <div id="collapseTurnos" class="collapse" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">Opciones:</h6>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/turnos">Ver Turnos</a>
                    </div>
                </div>
            </li>
        </c:when>
        <c:otherwise>
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTurnos"
                   aria-expanded="true" aria-controls="collapseTurnos">
                    <i class="fas fa-calendar-check"></i>
                    <span>Turnos</span>
                </a>
                <div id="collapseTurnos" class="collapse" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <h6 class="collapse-header">Opciones:</h6>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/turnos">Ver Turnos</a>
                        <c:if test="${usuario.rol == 'ADMIN' || usuario.rol == 'SECRETARIO'}">
                            <a class="collapse-item" href="${pageContext.request.contextPath}/turnos/agendar">Agendar Turno</a>
                        </c:if>
                    </div>
                </div>
            </li>
        </c:otherwise>
    </c:choose>

    <!-- Horarios -->
    <c:if test="${usuario.rol == 'ADMIN' || usuario.rol == 'SECRETARIO'}">
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseHorarios"
               aria-expanded="true" aria-controls="collapseHorarios">
                <i class="fas fa-clock"></i>
                <span>Horarios</span>
            </a>
            <div id="collapseHorarios" class="collapse" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Opciones:</h6>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/horarios">Ver Horarios</a>
                    <c:if test="${usuario.rol == 'ADMIN'}">
                        <a class="collapse-item" href="${pageContext.request.contextPath}/horarios/asignar">Asignar Horario</a>
                    </c:if>
                </div>
            </div>
        </li>
    </c:if>

    <hr class="sidebar-divider d-none d-md-block">

</ul>
<!-- Fin del sidebar -->
