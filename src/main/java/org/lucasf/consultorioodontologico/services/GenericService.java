package org.lucasf.consultorioodontologico.services;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {

    Optional<T> porId(Long id) throws Exception;
    List<T> listar() throws Exception;
    void guardar(T t) throws Exception;
    void eliminar(Long id) throws Exception;
}
