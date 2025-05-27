package org.lucasf.consultorioodontologico.services;

import jakarta.inject.Inject;
import org.lucasf.consultorioodontologico.configs.Service;
import org.lucasf.consultorioodontologico.interceptors.TransactionalJpa;
import org.lucasf.consultorioodontologico.models.entities.Odontologo;
import org.lucasf.consultorioodontologico.models.entities.Paciente;
import org.lucasf.consultorioodontologico.models.entities.Responsable;
import org.lucasf.consultorioodontologico.models.entities.Turno;
import org.lucasf.consultorioodontologico.repositories.TurnoRepository;
import org.lucasf.consultorioodontologico.repositories.personas.PacienteRepository;
import org.lucasf.consultorioodontologico.repositories.personas.ResponsableRepository;

import java.util.List;
import java.util.Optional;

@Service
@TransactionalJpa
public class PacienteServiceImpl implements PacienteService {

    @Inject
    private PacienteRepository pacienteRepository;

    @Inject
    private TurnoRepository turnoRepository;


    @Override
    public List<Paciente> tieneObraSocial(boolean obraSocial) {
        return pacienteRepository.tieneObraSocial(obraSocial);
    }

    @Override
    public List<Paciente> buscarPorTipoSangre(String tipoSangre) {
        return pacienteRepository.porTipoSangre(tipoSangre);
    }

    @Override
    public List<Turno> turnosPorPaciente(Long idPaciente) throws Exception {
        return turnoRepository.porPaciente(idPaciente);
    }

    @Override
    public List<Paciente> porResponsable(Long responsableId) throws Exception {
        return pacienteRepository.porResponsable(responsableId);
    }

    @Override
    public List<Paciente> buscarPorOdontologo(Long odontologoId) {
        return pacienteRepository.buscarPorOdontologo(odontologoId);
    }

    @Override
    public Optional<Paciente> porId(Long id) throws Exception {
        return Optional.ofNullable(pacienteRepository.porId(id));
    }

    @Override
    public List<Paciente> listar() throws Exception {
        return pacienteRepository.listar();
    }

    @Override
    public void guardar(Paciente paciente) throws Exception {
        pacienteRepository.guardar(paciente);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (!turnoRepository.porPaciente(id).isEmpty()) {
            throw new Exception("No se puede eliminar el paciente porque tiene turnos agendados.");
        }
        pacienteRepository.eliminar(id);
    }
}
