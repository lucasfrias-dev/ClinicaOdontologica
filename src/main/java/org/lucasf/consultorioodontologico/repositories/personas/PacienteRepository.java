package org.lucasf.consultorioodontologico.repositories.personas;

import org.lucasf.consultorioodontologico.models.entities.Paciente;

import java.util.List;

public interface PacienteRepository extends PersonaRepository<Paciente> {

    List<Paciente> tieneObraSocial(boolean obraSocial);
    List<Paciente> porTipoSangre(String tipoSangre);
    List<Paciente> porResponsable(Long responsableId) throws Exception;

    List<Paciente> buscarPorOdontologo(Long odontologoId);
}
