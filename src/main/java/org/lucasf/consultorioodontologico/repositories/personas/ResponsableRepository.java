package org.lucasf.consultorioodontologico.repositories.personas;

import org.lucasf.consultorioodontologico.models.entities.Responsable;

import java.util.List;

public interface ResponsableRepository extends PersonaRepository<Responsable> {

    List<Responsable> porResponsabilidad(String tipo);
}
