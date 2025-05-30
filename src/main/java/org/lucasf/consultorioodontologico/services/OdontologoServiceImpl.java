package org.lucasf.consultorioodontologico.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lucasf.consultorioodontologico.configs.Service;
import org.lucasf.consultorioodontologico.interceptors.TransactionalJpa;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Turno;
import org.lucasf.consultorioodontologico.models.entities.Usuario;
import org.lucasf.consultorioodontologico.repositories.HorarioRepository;
import org.lucasf.consultorioodontologico.repositories.TurnoRepository;
import org.lucasf.consultorioodontologico.repositories.personas.OdontologoRepository;
import org.lucasf.consultorioodontologico.utils.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@TransactionalJpa
public class OdontologoServiceImpl implements OdontologoService{

    @Inject
    private OdontologoRepository odontologoRepository;

    @Inject
    private HorarioRepository horarioRepository;

    @Inject
    private TurnoRepository turnoRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UsuarioService usuarioService;

    @Override
    public List<Odontologo> buscarPorMatricula(String matricula) throws Exception {
        return odontologoRepository.porMatricula(matricula);
    }

    @Override
    public List<Odontologo> buscarPorEspecialidad(String especialidad) throws Exception {
        return odontologoRepository.porEspecialidad(especialidad);
    }

    @Override
    public Optional<Odontologo> buscarPorUsuarioId(Long idUsuario) {
        return odontologoRepository.buscarPorUsuarioId(idUsuario);
    }

    @Override
    public List<Turno> turnosPorOdontologo(Long idOdontologo) throws Exception {
        return turnoRepository.porOdontologo(idOdontologo);
    }

    @Override
    public List<Horario> horariosPorOdontologo(Long idOdontologo) throws Exception {
        return horarioRepository.porOdontologo(idOdontologo);
    }

    @Override
    public Optional<Odontologo> porId(Long id) throws Exception {
        return Optional.ofNullable(odontologoRepository.porId(id));
    }

    @Override
    public List<Odontologo> listar() throws Exception {
        return odontologoRepository.listar();
    }

    @Override
    public void guardar(Odontologo odontologo) throws Exception {
        Usuario usuario = odontologo.getUsuario();

        String hashed = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(hashed);

        odontologoRepository.guardar(odontologo);
    }

    @Override
    public void eliminar(Long id) throws Exception {

        Usuario usuario = odontologoRepository.porId(id).getUsuario();
        if (usuario != null) {
            usuarioService.eliminar(usuario.getIdUsuario());
        }

        odontologoRepository.eliminar(id);
    }
}
