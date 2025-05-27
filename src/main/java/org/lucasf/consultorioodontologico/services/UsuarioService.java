package org.lucasf.consultorioodontologico.services;

import org.lucasf.consultorioodontologico.models.entities.Usuario;

public interface UsuarioService extends GenericService<Usuario> {

    Usuario buscarPorNombreUsuario(String nombreUsuario) throws Exception;
    Usuario buscarPorPersonal(Long idPersonal) throws Exception;
}
