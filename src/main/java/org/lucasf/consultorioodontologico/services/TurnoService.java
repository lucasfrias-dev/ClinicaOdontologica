package org.lucasf.consultorioodontologico.services;

import org.lucasf.consultorioodontologico.dtos.TurnoDisponibleDTO;
import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Turno;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TurnoService extends GenericService<Turno> {

    List<Turno> buscarPorPaciente(Long pacienteId) throws Exception;
    List<Turno> buscarPorOdontologo(Long odontologoId) throws Exception;
    List<Turno> buscarPorFecha(LocalDate fecha) throws Exception;

    List<TurnoDisponibleDTO> obtenerTurnosDisponibles(Long odontologoId) throws Exception;
}
