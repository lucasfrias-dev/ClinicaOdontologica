package org.lucasf.consultorioodontologico.services;

import org.lucasf.consultorioodontologico.models.entities.Responsable;

import java.util.List;

public interface ResponsableService extends GenericService<Responsable> {

    List<Responsable> buscarPorResponsabilidad(String responsabilidad) throws Exception;
}
