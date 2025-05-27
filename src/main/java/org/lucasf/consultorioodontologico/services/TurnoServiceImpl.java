package org.lucasf.consultorioodontologico.services;

import jakarta.inject.Inject;
import org.lucasf.consultorioodontologico.configs.Service;
import org.lucasf.consultorioodontologico.dtos.TurnoDisponibleDTO;
import org.lucasf.consultorioodontologico.interceptors.TransactionalJpa;
import org.lucasf.consultorioodontologico.models.DiaSemana;
import org.lucasf.consultorioodontologico.models.entities.Horario;
import org.lucasf.consultorioodontologico.models.entities.Turno;
import org.lucasf.consultorioodontologico.repositories.HorarioRepository;
import org.lucasf.consultorioodontologico.repositories.TurnoRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@TransactionalJpa
public class TurnoServiceImpl implements TurnoService {

    private static final int DURACION_TURNO_MINUTOS = 30;

    @Inject
    private TurnoRepository turnoRepository;

    @Inject
    private HorarioRepository horarioRepository;

    @Override
    public List<Turno> buscarPorPaciente(Long pacienteId) throws Exception {
        return turnoRepository.porPaciente(pacienteId);
    }

    @Override
    public List<Turno> buscarPorOdontologo(Long odontologoId) throws Exception {
        return turnoRepository.porOdontologo(odontologoId);
    }

    @Override
    public List<Turno> buscarPorFecha(LocalDate fecha) throws Exception {
        return turnoRepository.porFecha(fecha);
    }

    @Override
    public Optional<Turno> porId(Long id) throws Exception {
        return Optional.ofNullable(turnoRepository.porId(id));
    }

    @Override
    public List<Turno> listar() throws Exception {
        return turnoRepository.listar();
    }

    @Override
    public void guardar(Turno turno) throws Exception {
        turnoRepository.guardar(turno);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        turnoRepository.eliminar(id);
    }

    @Override
    public List<TurnoDisponibleDTO> obtenerTurnosDisponibles(Long odontologoId) throws Exception {

        List<TurnoDisponibleDTO> turnosDisponibles = new ArrayList<>();
        List<Horario> horariosOdontologo = horarioRepository.porOdontologo(odontologoId);
        List<Turno> turnosOcupados = turnoRepository.porOdontologo(odontologoId);

        // Convertimos los turnos ocupados a pares de fecha y hora
        Set<LocalDateTime> turnosOcupadosDateTime = turnosOcupados.stream()
                .map(t -> LocalDateTime.of(t.getFechaTurno(), t.getHoraTurno()))
                .collect(Collectors.toSet());

        LocalDate hoy = LocalDate.now();
        LocalDate hasta = hoy.plusWeeks(4); // mostrar turnos disponibles por las próximas 4 semanas

        for (LocalDate fecha = hoy; !fecha.isAfter(hasta); fecha = fecha.plusDays(1)) {
            DiaSemana dia = DiaSemana.desdeFecha(fecha); // convertimos al enum tuyo

            for (Horario horario : horariosOdontologo) {
                if (horario.getDia().equals(dia)) {
                    LocalTime horaInicio = horario.getHoraInicio();
                    LocalTime horaFin = horario.getHoraFin();
                    LocalTime horaActual = horaInicio;

                    while (!horaActual.plusMinutes(30).isAfter(horaFin)) {
                        LocalDateTime posibleTurno = LocalDateTime.of(fecha, horaActual);

                        if (!turnosOcupadosDateTime.contains(posibleTurno)) {
                            turnosDisponibles.add(new TurnoDisponibleDTO(posibleTurno));
                        }

                        horaActual = horaActual.plusMinutes(30);
                    }
                }
            }
        }

        return turnosDisponibles;
    }
}
