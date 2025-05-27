package org.lucasf.consultorioodontologico.services;

import jakarta.inject.Inject;
import org.lucasf.consultorioodontologico.configs.Service;
import org.lucasf.consultorioodontologico.interceptors.NoTransactional;
import org.lucasf.consultorioodontologico.interceptors.TransactionalJpa;
import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.repositories.HorarioRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@TransactionalJpa
public class HorarioServiceImpl implements HorarioService {

    @Inject
    private HorarioRepository horarioRepository;

    @Inject
    private OdontologoService odontologoService;

    @Override
    public List<Horario> buscarPorDia(DiaSemana dia) throws Exception {
        return horarioRepository.porDia(dia);
    }

    @Override
    public List<Horario> buscarPorOdontologo(Long odontologoId) throws Exception {
        return horarioRepository.porOdontologo(odontologoId);
    }

    @Override
    public void asignarHorario(Odontologo odontologo, Horario horario) throws Exception {
        try {
            // Primero guarda el horario si es nuevo
            if (horario.getIdHorario() == null) {
                horarioRepository.guardar(horario);
            }

            // Verifica si el horario ya está asignado
            if (!odontologo.getHorarios().contains(horario)) {
                odontologo.getHorarios().add(horario);
                odontologoService.guardar(odontologo);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al asignar horario: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Horario> porId(Long id) throws Exception {
        return Optional.ofNullable(horarioRepository.porId(id));
    }

    @Override
    @NoTransactional
    public List<Horario> listar() throws Exception {
        return horarioRepository.listar();
    }

    @Override
    public void guardar(Horario horario) throws Exception {
        horarioRepository.guardar(horario);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        horarioRepository.eliminar(id);
    }
}
