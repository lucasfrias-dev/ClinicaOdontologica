package org.lucasf.consultorioodontologico.repositories.personas;

import org.lucasf.consultorioodontologico.models.entities.Odontologo;

import java.util.List;
import java.util.Optional;

public interface OdontologoRepository extends PersonaRepository<Odontologo> {

    List<Odontologo> porMatricula(String matricula) throws Exception;
    List<Odontologo> porEspecialidad(String especialidad) throws Exception;

    Optional<Odontologo> buscarPorUsuarioId(Long idUsuario);
}
