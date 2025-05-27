package org.lucasf.consultorioodontologico.repositories.personas;

import org.lucasf.consultorioodontologico.models.entities.Persona;
import org.lucasf.consultorioodontologico.repositories.CrudRepository;

import java.util.List;

public interface PersonaRepository<T extends Persona> extends CrudRepository<T> {

    T porDni(String dni) throws Exception;
    List<T> porNombre(String nombre);
    List<T> porApellido(String apellido) throws Exception;
}
