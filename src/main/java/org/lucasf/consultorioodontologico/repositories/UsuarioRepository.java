package org.lucasf.consultorioodontologico.repositories;


import org.lucasf.consultorioodontologico.models.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario> {

    Usuario porNombreUsuario(String nombreUsuario) throws Exception;

    Usuario porPersonal(Long idPersonal) throws Exception;
}
