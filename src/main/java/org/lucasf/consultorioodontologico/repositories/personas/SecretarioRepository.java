package org.lucasf.consultorioodontologico.repositories.personas;

import org.lucasf.consultorioodontologico.models.entities.Secretario;

import java.util.List;

public interface SecretarioRepository extends PersonaRepository<Secretario> {

    List<Secretario> porSector(String sector);
}
