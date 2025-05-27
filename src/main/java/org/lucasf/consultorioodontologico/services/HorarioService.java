package org.lucasf.consultorioodontologico.services;

import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;

import java.time.LocalDate;
import java.util.List;

public interface HorarioService extends GenericService<Horario> {

    List<Horario> buscarPorDia(DiaSemana dia) throws Exception;
    List<Horario> buscarPorOdontologo(Long odontologoId) throws Exception;
    void asignarHorario(Odontologo odontologo, Horario horario) throws Exception;
}
