package org.lucasf.consultorioodontologico.services;

import jakarta.inject.Inject;
import org.lucasf.consultorioodontologico.configs.Service;
import org.lucasf.consultorioodontologico.interceptors.TransactionalJpa;
import org.lucasf.consultorioodontologico.models.entities.Secretario;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.repositories.personas.SecretarioRepository;
import org.lucasf.consultorioodontologico.utils.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@TransactionalJpa
public class SecretarioServiceImpl implements SecretarioService {

    @Inject
    private SecretarioRepository secretarioRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UsuarioService usuarioService;

    @Override
    public List<Secretario> buscarPorSector(String sector) throws Exception {
        return secretarioRepository.porSector(sector);
    }

    @Override
    public Optional<Secretario> porId(Long id) throws Exception {
        return Optional.ofNullable(secretarioRepository.porId(id));
    }

    @Override
    public List<Secretario> listar() throws Exception {
        return secretarioRepository.listar();
    }

    @Override
    public void guardar(Secretario secretario) throws Exception {
        Usuario usuario = secretario.getUsuario();

        String hashed = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(hashed);

        secretarioRepository.guardar(secretario);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        Usuario usuario = secretarioRepository.porId(id).getUsuario();
        if (usuario != null) {
            usuarioService.eliminar(usuario.getIdUsuario());
        }

        secretarioRepository.eliminar(id);
    }
}
