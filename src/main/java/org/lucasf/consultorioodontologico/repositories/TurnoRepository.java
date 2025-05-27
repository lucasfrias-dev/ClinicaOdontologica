package org.lucasf.consultorioodontologico.repositories;

import org.lucasf.consultorioodontologico.models.entities.Turno;

import java.time.LocalDate;
import java.util.List;

public interface TurnoRepository extends CrudRepository<Turno> {

    List<Turno> porFecha(LocalDate fecha);
    List<Turno> porOdontologo(Long odontologoId);
    List<Turno> porPaciente(Long pacienteId);
}
