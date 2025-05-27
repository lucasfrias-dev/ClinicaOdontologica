package org.lucasf.consultorioodontologico.services;

import org.lucasf.consultorioodontologico.models.entities.Paciente;
import org.lucasf.consultorioodontologico.models.entities.Responsable;
import org.lucasf.consultorioodontologico.models.entities.Turno;

import java.util.List;
import java.util.Optional;

public interface PacienteService extends GenericService<Paciente> {

    List<Paciente> tieneObraSocial(boolean obraSocial);
    List<Paciente> buscarPorTipoSangre(String tipoSangre);

    List<Turno> turnosPorPaciente(Long idPaciente) throws Exception;
    List<Paciente> porResponsable(Long responsableId) throws Exception;

    List<Paciente> buscarPorOdontologo(Long odontologoId);
}
