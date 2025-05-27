package org.lucasf.consultorioodontologico.repositories;

import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Horario;

import java.time.LocalDate;
import java.util.List;

public interface HorarioRepository extends CrudRepository<Horario> {

    List<Horario> porDia(DiaSemana dia);
    List<Horario> porOdontologo(Long odontologoId) throws Exception;
}
