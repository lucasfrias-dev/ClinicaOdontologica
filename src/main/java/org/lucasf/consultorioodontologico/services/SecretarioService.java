package org.lucasf.consultorioodontologico.services;

import org.lucasf.consultorioodontologico.models.entities.Secretario;

import java.util.List;

public interface SecretarioService extends GenericService<Secretario> {

    List<Secretario> buscarPorSector(String sector) throws Exception;
}
