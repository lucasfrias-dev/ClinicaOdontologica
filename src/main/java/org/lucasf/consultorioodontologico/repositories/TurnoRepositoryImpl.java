package org.lucasf.consultorioodontologico.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lucasf.consultorioodontologico.configs.Repository;
import org.lucasf.consultorioodontologico.models.entities.Turno;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TurnoRepositoryImpl implements TurnoRepository{

    @Inject
    private EntityManager em;

    @Override
    public List<Turno> porFecha(LocalDate fecha) {
        return em.createQuery("select t from turnos t where t.fechaTurno = :fecha", Turno.class)
                .setParameter("fecha", fecha)
                .getResultList();
    }

    @Override
    public List<Turno> porOdontologo(Long odontologoId) {
        return em.createQuery("select t from turnos t where t.odontologo.usuario.id = :odontologoId", Turno.class)
                .setParameter("odontologoId", odontologoId)
                .getResultList();
    }

    @Override
    public List<Turno> porPaciente(Long pacienteId) {
        return em.createQuery("select t from turnos t where t.paciente.id = :pacienteId", Turno.class)
                .setParameter("pacienteId", pacienteId)
                .getResultList();
    }

    @Override
    public List<Turno> listar() throws Exception {
        return em.createQuery("select t from turnos t", Turno.class)
                .getResultList();
    }

    @Override
    public Turno porId(Long id) throws Exception {
        return em.find(Turno.class, id);
    }

    @Override
    public void guardar(Turno turno) throws Exception {
        if (turno.getIdTurno() != null && turno.getIdTurno() > 0) {
            em.merge(turno);
        } else {
            em.persist(turno);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        em.remove(porId(id));
    }
}
