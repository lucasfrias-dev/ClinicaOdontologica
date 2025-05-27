package org.lucasf.consultorioodontologico.services;

import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Turno;

import java.util.List;

public interface OdontologoService extends GenericService<Odontologo> {

    List<Odontologo> buscarPorMatricula(String matricula) throws Exception;
    List<Odontologo> buscarPorEspecialidad(String especialidad) throws Exception;

    List<Turno> turnosPorOdontologo(Long idOdontologo) throws Exception;
    List<Horario> horariosPorOdontologo(Long idOdontologo) throws Exception;
}
