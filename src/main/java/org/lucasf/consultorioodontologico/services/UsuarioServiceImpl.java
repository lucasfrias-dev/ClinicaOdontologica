package org.lucasf.consultorioodontologico.services;

import jakarta.inject.Inject;
import org.lucasf.consultorioodontologico.configs.Service;
import org.lucasf.consultorioodontologico.interceptors.TransactionalJpa;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@TransactionalJpa
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> porId(Long id) throws Exception {
        return Optional.ofNullable(usuarioRepository.porId(id));
    }

    @Override
    public List<Usuario> listar() throws Exception {
        return usuarioRepository.listar();
    }

    @Override
    public void guardar(Usuario usuario) throws Exception {
        usuarioRepository.guardar(usuario);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        usuarioRepository.eliminar(id);
    }

    @Override
    public Usuario buscarPorNombreUsuario(String nombreUsuario) throws Exception {
        return usuarioRepository.porNombreUsuario(nombreUsuario);
    }

    @Override
    public Usuario buscarPorPersonal(Long idPersonal) throws Exception {
        return usuarioRepository.porPersonal(idPersonal);
    }
}
